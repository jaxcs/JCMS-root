/*
 * Loading...
 */
jQuery.ajaxSetup({
	beforeSend: function() {
		$('.loader').show();
	},
	complete: function(){
		$('.loader').hide();
	},
	success: function() {}
});

/**
 * Function called onload to build the colony summary report. The tables and headers are 
 * hardcoded in the html template (colonySummaryReport.html) but the rows will vary from
 * database to database according to # of owners and # of strains so the table content
 * has to be build dynamically at the row level for the tables aside from summary stats.
 * 
 * @param - none
 * @returns - none
 * @author mkamato
 */
function buildColonySummaryReport(){
	var url;
	if(getSecure()){
		url = 'https://' + getHost() + ':8443';
	}
	else{
		url = 'http://' + getHost() + ':8080';
	}
	$.ajax({
		type: 'GET',
		url: url + '/jcms/colonySummary/generateData/' + getUser() + '/' + getEncrypedPW(),
		success: function (data) {
			$("#containerDiv").attr('style', '');
			var colonySummaryData = JSON.parse(data);
			$(function(){
				$("#numActiveMatings").text(colonySummaryData.summaryStats.matingCount);
				$("#numActiveCages").text(colonySummaryData.summaryStats.cageCount);
				$("#numActiveStrains").text(colonySummaryData.summaryStats.strainCount);
				$("#numLiveMice").text(colonySummaryData.summaryStats.mouseCount);
				/*
				 * Mouse stats:
				 * 
				 * Mouse stats are grouped by owner then strains. In the row that defines
				 * the owner should also be a cell w/ the total number strains that owner
				 * has mice on shelf for as well as total mice that owner has on the shelf.
				 * 
				 * After that row comes the individual strain rows. Each strain row should
				 * contain the strain name and total number of mice that owner has of
				 * that strain:
				 * 
				 * |Owner/Workgroup | Strain	         | # Live Mice 		|
				 * | ownerName      | # total Strains: 5 | # Total Mice: 150|<--Owner item
				 * | 		        | StrainName1    	 |  	15    		|<--Strain item
				 * |			  	| StrainName2    	 |  	21			|
				 * 						.
				 * 						.
				 * 						.
				 * Point being, each tr for an 'owner item' should be differently composed
				 * (and styled) than the strain items for that owner.
				 */
				$.each(colonySummaryData.mouseStats, function(i, ownerItem){
					//first the owner item row...
					$("#miceTable").append(
							"<tbody>" +
								"<tr class='info'>" +
									"<td>" + ownerItem.owner + "</td>" +
									"<td># Total Strains: " + ownerItem.totalStrains + "</td>" +
									"<td># Total Mice: " + ownerItem.totalMice + "</td>" +
								"</tr>" +
							"</tbody>");
					//then the strain items for that owner...
					$.each(ownerItem.strains, function(i, strainItem){
						$("#miceTable").append(
								"<tbody>" +
									"<tr>" +
										"<td>&nbsp;</td>" +
										"<td>" + strainItem.strainName + "</td>" +
										"<td>" + strainItem.totalMice + "</td>" +
									"</tr>" +
								"</tbody>");
					});
				});
				
				
				/*
				 * Cage Stats:
				 * 
				 * Same theory and format as mice stats from above but instead of
				 * owner and strain it is organized by owner then room.
				 */
				$.each(colonySummaryData.cageStats, function(i, ownerItem){
					//first row the owner item row - total cages, total rooms$
					$("#cagesTable").append(
						"<tbody>" +
							"<tr class='info'>" +
								"<td>" + ownerItem.owner + "</td>" +
								"<td># Total Rooms: " + ownerItem.totalRooms + "</td>" +
								"<td># Total Cages: " + ownerItem.totalCages + "</td>" +
							"</tr>" +
						"</tbody>");
					//then the room items for that owner...
					$.each(ownerItem.rooms, function(i, roomItem){
						$("#cagesTable").append(
								"<tbody>" +
									"<tr>" +
										"<td>&nbsp;</td>" +
										"<td>" + roomItem.roomName + "</td>" +
										"<td>" + roomItem.totalCages + "</td>" +
									"</tr>" +
								"</tbody>"
						);
					});
				});
				
				
				/*
				 * Mating stats:
				 * 
				 * Same as mice stats, organized by owner and strain.
				 */
				$.each(colonySummaryData.matingStats, function(i, ownerItem){
					//first the owner item row...
					$("#matingsTable").append(
							"<tbody>" +
								"<tr class='info'>" +
									"<td>" + ownerItem.owner + "</td>" +
									"<td># Total Strains: " + ownerItem.totalStrains + "</td>" +
									"<td># Total Matings: " + ownerItem.totalMatings + "</td>" +
								"</tr>" +
							"</tbody>");
					//then the strain items for that owner...
					$.each(ownerItem.strains, function(i, strainItem){
						$("#matingsTable").append(
								"<tbody>" +
									"<tr>" +
										"<td>&nbsp;</td>" +
										"<td>" + strainItem.strainName + "</td>" +
										"<td>" + strainItem.totalMatings + "</td>" +
									"</tr>" +
								"</tbody>");
					});
				});
			});
		},
		error: function (xhr, status, error) {

		}
	});
}