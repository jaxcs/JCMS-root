#***
#Copyright (c) 2015 The Jackson Laboratory
#
#This is free software: you can redistribute it and/or modify it 
#under the terms of the GNU General Public License as published by  
#the Free Software Foundation, either version 3 of the License, or  
#(at your option) any later version.
# 
#This software is distributed in the hope that it will be useful,  
#but WITHOUT ANY WARRANTY; without even the implied warranty of 
#MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
#General Public License for more details.
#
#You should have received a copy of the GNU General Public License 
#along with this software.  If not, see <http://www.gnu.org/licenses/>.
#***
-- JCMS Web script to replace histoProcedure with a new version that counts
-- the cages correctly for the cage histogram

SELECT '-- Create Histogram Procedure --';
DROP PROCEDURE IF EXISTS `histoProcedure`;
DELIMITER $$

CREATE PROCEDURE histoProcedure()
	BEGIN
		DECLARE idx INT Default 1 ;
		DECLARE theDay DATETIME DEFAULT NOW();

		DELETE FROM `HistogramDataTable` WHERE unitType != '';

		initHistoCageOwnerLoop: LOOP
			SET idx = idx - 1;

			IF idx < -30 THEN
				LEAVE initHistoCageOwnerLoop;
			END IF;

			SET theDay = DATE_ADD(DATE(DATE_ADD(NOW(), INTERVAL idx DAY)), INTERVAL '23:59:59' HOUR_SECOND);

			INSERT INTO `HistogramDataTable`
			SELECT 'cages' as unitType, Mouse.owner, strainName, DATE(theDay) AS `date`, COUNT(DISTINCT(Container._container_key)) AS activeCages
			FROM Container
			JOIN (
				Select _container_key, _containerHistory_key, _room_key, _containerStatus_key, actionDate
                			From ContainerHistory ch1
                			where actionDate =
                    				(select max(ch2.actionDate)
                					from ContainerHistory ch2
                					where ch2.actionDate <= DATE(theDay)
							and ch1._container_key = ch2._container_key)

				) ContainerHistory
			ON ContainerHistory._container_key = Container._container_key
			JOIN Room
			ON ContainerHistory._room_key = Room._room_key
			JOIN cv_ContainerStatus
			ON ContainerHistory._containerStatus_key = cv_ContainerStatus._containerStatus_key
			JOIN Mouse
			ON Mouse._pen_key = Container._container_key
			JOIN Strain
			ON Mouse._strain_key = Strain._strain_key
			WHERE billable != 0
			AND ContainerHistory.actionDate < theDay
			AND owner IS NOT NULL
			GROUP BY Mouse.owner, Strain._strain_key;

			INSERT INTO `HistogramDataTable`
			SELECT 'mice' as unitType, `owner`, strainName, DATE(theDay) AS `date`, COUNT(*) AS mice
			FROM Mouse
			JOIN Strain
			ON Mouse._strain_key = Strain._strain_key
			WHERE birthDate < theDay
			AND (exitDate IS NULL || exitDate > theDay)
			GROUP BY `owner`, Strain._strain_key;

		END LOOP initHistoCageOwnerLoop;
	END $$

DELIMITER ;

SELECT '-- Histogram Procedure - Running --';
CALL histoProcedure();
SELECT '-- Histogram Procedure - Complete --';

UPDATE dbInfo SET
	dbVers = 141,
	webReleaseNum= '3.14.0',
	versDate = now(),
	releaseDate = now()
    WHERE _dbinfo_key = 1
;

SELECT '-- Database upgrade 141 complete --';

