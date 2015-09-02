/****
Copyright (c) 2015 The Jackson Laboratory

This is free software: you can redistribute it and/or modify it 
under the terms of the GNU General Public License as published by  
the Free Software Foundation, either version 3 of the License, or  
(at your option) any later version.
 
This software is distributed in the hope that it will be useful,  
but WITHOUT ANY WARRANTY; without even the implied warranty of 
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
General Public License for more details.

You should have received a copy of the GNU General Public License 
along with this software.  If not, see <http://www.gnu.org/licenses/>.
****/

package jcms.web.colonyManagement;

import java.io.Serializable;
import org.richfaces.component.SortOrder;

/**
 *
 * @author cnh
 */
public class Sort implements Serializable {
    private static long serialVersionUID = -6237417487105926855L;

    private String unsortedImage = "/images/arrow-unsorted.gif";
    private String ascendingImage = "/images/arrow-up.gif";
    private String descendingImage = "/images/arrow-down.gif";
             
    private SortOrder holdOrder = SortOrder.unsorted;
    
    private SortOrder column1Order = SortOrder.unsorted;
    private SortOrder column2Order = SortOrder.unsorted;
    private SortOrder column3Order = SortOrder.unsorted;
    private SortOrder column4Order = SortOrder.unsorted;
    private SortOrder column5Order = SortOrder.unsorted;
    private SortOrder column6Order = SortOrder.unsorted;
    private SortOrder column7Order = SortOrder.unsorted;
    private SortOrder column8Order = SortOrder.unsorted;
    private SortOrder column9Order = SortOrder.unsorted;
    private SortOrder column10Order = SortOrder.unsorted;
    private SortOrder column11Order = SortOrder.unsorted;
    private SortOrder column12Order = SortOrder.unsorted;
    private SortOrder column13Order = SortOrder.unsorted;
    private SortOrder column14Order = SortOrder.unsorted;
    private SortOrder column15Order = SortOrder.unsorted;
    private SortOrder column16Order = SortOrder.unsorted;
    private SortOrder column17Order = SortOrder.unsorted;
    private SortOrder column18Order = SortOrder.unsorted;
    private SortOrder column19Order = SortOrder.unsorted;
    private SortOrder column20Order = SortOrder.unsorted;
    private SortOrder column21Order = SortOrder.unsorted;
    private SortOrder column22Order = SortOrder.unsorted;
    private SortOrder column23Order = SortOrder.unsorted;
    private SortOrder column24Order = SortOrder.unsorted;
    private SortOrder column25Order = SortOrder.unsorted;
    private SortOrder column26Order = SortOrder.unsorted;
    private SortOrder column27Order = SortOrder.unsorted;
    private SortOrder column28Order = SortOrder.unsorted;
    private SortOrder column29Order = SortOrder.unsorted;
    private SortOrder column30Order = SortOrder.unsorted;
    private SortOrder column31Order = SortOrder.unsorted;
    private SortOrder column32Order = SortOrder.unsorted;
    private SortOrder column33Order = SortOrder.unsorted;
    private SortOrder column34Order = SortOrder.unsorted;
    private SortOrder column35Order = SortOrder.unsorted;
    private SortOrder column36Order = SortOrder.unsorted;
    private SortOrder column37Order = SortOrder.unsorted;
    private SortOrder column38Order = SortOrder.unsorted;
    private SortOrder column39Order = SortOrder.unsorted;
    private SortOrder column40Order = SortOrder.unsorted;
    private SortOrder column41Order = SortOrder.unsorted;
    private SortOrder column42Order = SortOrder.unsorted;
    private SortOrder column43Order = SortOrder.unsorted;
    private SortOrder column44Order = SortOrder.unsorted;
    private SortOrder column45Order = SortOrder.unsorted;
    private SortOrder column46Order = SortOrder.unsorted;
    private SortOrder column47Order = SortOrder.unsorted;
    private SortOrder column48Order = SortOrder.unsorted;
    private SortOrder column49Order = SortOrder.unsorted;
    private SortOrder column50Order = SortOrder.unsorted;
    
    private String column1Image = unsortedImage;
    private String column2Image = unsortedImage;
    private String column3Image = unsortedImage;
    private String column4Image = unsortedImage;
    private String column5Image = unsortedImage;
    private String column6Image = unsortedImage;
    private String column7Image = unsortedImage;
    private String column8Image = unsortedImage;
    private String column9Image = unsortedImage;
    private String column10Image = unsortedImage;
    private String column11Image = unsortedImage;
    private String column12Image = unsortedImage;
    private String column13Image = unsortedImage;
    private String column14Image = unsortedImage;
    private String column15Image = unsortedImage;
    private String column16Image = unsortedImage;
    private String column17Image = unsortedImage;
    private String column18Image = unsortedImage;
    private String column19Image = unsortedImage;
    private String column20Image = unsortedImage;
    private String column21Image = unsortedImage;
    private String column22Image = unsortedImage;
    private String column23Image = unsortedImage;
    private String column24Image = unsortedImage;
    private String column25Image = unsortedImage;
    private String column26Image = unsortedImage;
    private String column27Image = unsortedImage;
    private String column28Image = unsortedImage;
    private String column29Image = unsortedImage;
    private String column30Image = unsortedImage;
    private String column31Image = unsortedImage;
    private String column32Image = unsortedImage;
    private String column33Image = unsortedImage;
    private String column34Image = unsortedImage;
    private String column35Image = unsortedImage;
    private String column36Image = unsortedImage;
    private String column37Image = unsortedImage;
    private String column38Image = unsortedImage;
    private String column39Image = unsortedImage;
    private String column40Image = unsortedImage;
    private String column41Image = unsortedImage;
    private String column42Image = unsortedImage;
    private String column43Image = unsortedImage;
    private String column44Image = unsortedImage;
    private String column45Image = unsortedImage;
    private String column46Image = unsortedImage;
    private String column47Image = unsortedImage;
    private String column48Image = unsortedImage;
    private String column49Image = unsortedImage;
    private String column50Image = unsortedImage;
    
    private void resetOrder() {
        setColumn1Order(SortOrder.unsorted);
        setColumn2Order(SortOrder.unsorted);
        setColumn3Order(SortOrder.unsorted);
        setColumn4Order(SortOrder.unsorted);
        setColumn5Order(SortOrder.unsorted);
        setColumn6Order(SortOrder.unsorted);
        setColumn7Order(SortOrder.unsorted);
        setColumn8Order(SortOrder.unsorted);
        setColumn9Order(SortOrder.unsorted);
        setColumn10Order(SortOrder.unsorted);
        setColumn11Order(SortOrder.unsorted);
        setColumn12Order(SortOrder.unsorted);
        setColumn13Order(SortOrder.unsorted);
        setColumn14Order(SortOrder.unsorted);
        setColumn15Order(SortOrder.unsorted);
        setColumn16Order(SortOrder.unsorted);
        setColumn17Order(SortOrder.unsorted);
        setColumn18Order(SortOrder.unsorted);
        setColumn19Order(SortOrder.unsorted);
        setColumn20Order(SortOrder.unsorted);
        setColumn21Order(SortOrder.unsorted);
        setColumn22Order(SortOrder.unsorted);
        setColumn23Order(SortOrder.unsorted);
        setColumn24Order(SortOrder.unsorted);
        setColumn25Order(SortOrder.unsorted);
        setColumn26Order(SortOrder.unsorted);
        setColumn27Order(SortOrder.unsorted);
        setColumn28Order(SortOrder.unsorted);
        setColumn29Order(SortOrder.unsorted);
        setColumn30Order(SortOrder.unsorted);
        setColumn31Order(SortOrder.unsorted);
        setColumn32Order(SortOrder.unsorted);
        setColumn33Order(SortOrder.unsorted);
        setColumn34Order(SortOrder.unsorted);
        setColumn35Order(SortOrder.unsorted);
        setColumn36Order(SortOrder.unsorted);
        setColumn37Order(SortOrder.unsorted);
        setColumn38Order(SortOrder.unsorted);
        setColumn39Order(SortOrder.unsorted);
        setColumn40Order(SortOrder.unsorted);
        setColumn41Order(SortOrder.unsorted);
        setColumn42Order(SortOrder.unsorted);
        setColumn43Order(SortOrder.unsorted);
        setColumn44Order(SortOrder.unsorted);
        setColumn45Order(SortOrder.unsorted);
        setColumn46Order(SortOrder.unsorted);
        setColumn47Order(SortOrder.unsorted);
        setColumn48Order(SortOrder.unsorted);
        setColumn49Order(SortOrder.unsorted);
        setColumn50Order(SortOrder.unsorted);

        setColumn1Image(unsortedImage);
        setColumn2Image(unsortedImage);
        setColumn3Image(unsortedImage);
        setColumn4Image(unsortedImage);
        setColumn5Image(unsortedImage);
        setColumn6Image(unsortedImage);
        setColumn7Image(unsortedImage);
        setColumn8Image(unsortedImage);
        setColumn9Image(unsortedImage);
        setColumn10Image(unsortedImage);
        setColumn11Image(unsortedImage);
        setColumn12Image(unsortedImage);
        setColumn13Image(unsortedImage);
        setColumn14Image(unsortedImage);
        setColumn15Image(unsortedImage);
        setColumn16Image(unsortedImage);
        setColumn17Image(unsortedImage);
        setColumn18Image(unsortedImage);
        setColumn19Image(unsortedImage);
        setColumn20Image(unsortedImage);
        setColumn21Image(unsortedImage);
        setColumn22Image(unsortedImage);
        setColumn23Image(unsortedImage);
        setColumn24Image(unsortedImage);
        setColumn25Image(unsortedImage);
        setColumn26Image(unsortedImage);
        setColumn27Image(unsortedImage);
        setColumn28Image(unsortedImage);
        setColumn29Image(unsortedImage);
        setColumn30Image(unsortedImage);
        setColumn31Image(unsortedImage);
        setColumn32Image(unsortedImage);
        setColumn33Image(unsortedImage);
        setColumn34Image(unsortedImage);
        setColumn35Image(unsortedImage);
        setColumn36Image(unsortedImage);
        setColumn37Image(unsortedImage);
        setColumn38Image(unsortedImage);
        setColumn39Image(unsortedImage);
        setColumn40Image(unsortedImage);
        setColumn41Image(unsortedImage);
        setColumn42Image(unsortedImage);
        setColumn43Image(unsortedImage);
        setColumn44Image(unsortedImage);
        setColumn45Image(unsortedImage);
        setColumn46Image(unsortedImage);
        setColumn47Image(unsortedImage);
        setColumn48Image(unsortedImage);
        setColumn49Image(unsortedImage);
        setColumn50Image(unsortedImage);
    }
    
    public void sortByColumn1() {
        setHoldOrder(getColumn1Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn1Order(SortOrder.descending);
            setColumn1Image(getAscendingImage());
        } else {
            setColumn1Order(SortOrder.ascending);
            setColumn1Image(getDescendingImage());
        }
    }

    public void sortByColumn2() {
        setHoldOrder(getColumn2Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn2Order(SortOrder.descending);
            setColumn2Image(getAscendingImage());
        } else {
            setColumn2Order(SortOrder.ascending);
            setColumn2Image(getDescendingImage());
        }
    }

    public void sortByColumn3() {
        setHoldOrder(getColumn3Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn3Order(SortOrder.descending);
            setColumn3Image(getAscendingImage());
        } else {
            setColumn3Order(SortOrder.ascending);
            setColumn3Image(getDescendingImage());
        }
    }

    public void sortByColumn4() {
        setHoldOrder(getColumn4Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn4Order(SortOrder.descending);
            setColumn4Image(getAscendingImage());
        } else {
            setColumn4Order(SortOrder.ascending);
            setColumn4Image(getDescendingImage());
        }
    }

    public void sortByColumn5() {
        setHoldOrder(getColumn5Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn5Order(SortOrder.descending);
            setColumn5Image(getAscendingImage());
        } else {
            setColumn5Order(SortOrder.ascending);
            setColumn5Image(getDescendingImage());
        }
    }

    public void sortByColumn6() {
        setHoldOrder(getColumn6Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn6Order(SortOrder.descending);
            setColumn6Image(getAscendingImage());
        } else {
            setColumn6Order(SortOrder.ascending);
            setColumn6Image(getDescendingImage());
        }
    }

    public void sortByColumn7() {
        setHoldOrder(getColumn7Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn7Order(SortOrder.descending);
            setColumn7Image(getAscendingImage());
        } else {
            setColumn7Order(SortOrder.ascending);
            setColumn7Image(getDescendingImage());
        }
    }

    public void sortByColumn8() {
        setHoldOrder(getColumn8Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn8Order(SortOrder.descending);
            setColumn8Image(getAscendingImage());
        } else {
            setColumn8Order(SortOrder.ascending);
            setColumn8Image(getDescendingImage());
        }
    }

    public void sortByColumn9() {
        setHoldOrder(getColumn9Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn9Order(SortOrder.descending);
            setColumn9Image(getAscendingImage());
        } else {
            setColumn9Order(SortOrder.ascending);
            setColumn9Image(getDescendingImage());
        }
    }

    public void sortByColumn10() {
        setHoldOrder(getColumn10Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn10Order(SortOrder.descending);
            setColumn10Image(getAscendingImage());
        } else {
            setColumn10Order(SortOrder.ascending);
            setColumn10Image(getDescendingImage());
        }
    }

    public void sortByColumn11() {
        setHoldOrder(getColumn11Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn11Order(SortOrder.descending);
            setColumn11Image(getAscendingImage());
        } else {
            setColumn11Order(SortOrder.ascending);
            setColumn11Image(getDescendingImage());
        }
    }

    public void sortByColumn12() {
        setHoldOrder(getColumn12Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn12Order(SortOrder.descending);
            setColumn12Image(getAscendingImage());
        } else {
            setColumn12Order(SortOrder.ascending);
            setColumn12Image(getDescendingImage());
        }
    }

    public void sortByColumn13() {
        setHoldOrder(getColumn13Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn13Order(SortOrder.descending);
            setColumn13Image(getAscendingImage());
        } else {
            setColumn13Order(SortOrder.ascending);
            setColumn13Image(getDescendingImage());
        }
    }

    public void sortByColumn14() {
        setHoldOrder(getColumn14Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn14Order(SortOrder.descending);
            setColumn14Image(getAscendingImage());
        } else {
            setColumn14Order(SortOrder.ascending);
            setColumn14Image(getDescendingImage());
        }
    }

    public void sortByColumn15() {
        setHoldOrder(getColumn15Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn15Order(SortOrder.descending);
            setColumn15Image(getAscendingImage());
        } else {
            setColumn15Order(SortOrder.ascending);
            setColumn15Image(getDescendingImage());
        }
    }

    public void sortByColumn16() {
        setHoldOrder(getColumn16Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn16Order(SortOrder.descending);
            setColumn16Image(getAscendingImage());
        } else {
            setColumn16Order(SortOrder.ascending);
            setColumn16Image(getDescendingImage());
        }
    }

    public void sortByColumn17() {
        setHoldOrder(getColumn17Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn17Order(SortOrder.descending);
            setColumn17Image(getAscendingImage());
        } else {
            setColumn17Order(SortOrder.ascending);
            setColumn17Image(getDescendingImage());
        }
    }

    public void sortByColumn18() {
        setHoldOrder(getColumn18Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn18Order(SortOrder.descending);
            setColumn18Image(getAscendingImage());
        } else {
            setColumn18Order(SortOrder.ascending);
            setColumn18Image(getDescendingImage());
        }
    }

    public void sortByColumn19() {
        setHoldOrder(getColumn19Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn19Order(SortOrder.descending);
            setColumn19Image(getAscendingImage());
        } else {
            setColumn19Order(SortOrder.ascending);
            setColumn19Image(getDescendingImage());
        }
    }

    public void sortByColumn20() {
        setHoldOrder(getColumn20Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn20Order(SortOrder.descending);
            setColumn20Image(getAscendingImage());
        } else {
            setColumn20Order(SortOrder.ascending);
            setColumn20Image(getDescendingImage());
        }
    }

    public void sortByColumn21() {
        setHoldOrder(getColumn21Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn21Order(SortOrder.descending);
            setColumn21Image(getAscendingImage());
        } else {
            setColumn21Order(SortOrder.ascending);
            setColumn21Image(getDescendingImage());
        }
    }

    public void sortByColumn22() {
        setHoldOrder(getColumn22Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn22Order(SortOrder.descending);
            setColumn22Image(getAscendingImage());
        } else {
            setColumn22Order(SortOrder.ascending);
            setColumn22Image(getDescendingImage());
        }
    }

    public void sortByColumn23() {
        setHoldOrder(getColumn23Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn23Order(SortOrder.descending);
            setColumn23Image(getAscendingImage());
        } else {
            setColumn23Order(SortOrder.ascending);
            setColumn23Image(getDescendingImage());
        }
    }

    public void sortByColumn24() {
        setHoldOrder(getColumn24Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn24Order(SortOrder.descending);
            setColumn24Image(getAscendingImage());
        } else {
            setColumn24Order(SortOrder.ascending);
            setColumn24Image(getDescendingImage());
        }
    }

    public void sortByColumn25() {
        setHoldOrder(getColumn25Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn25Order(SortOrder.descending);
            setColumn25Image(getAscendingImage());
        } else {
            setColumn25Order(SortOrder.ascending);
            setColumn25Image(getDescendingImage());
        }
    }

    public void sortByColumn26() {
        setHoldOrder(getColumn26Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn26Order(SortOrder.descending);
            setColumn26Image(getAscendingImage());
        } else {
            setColumn26Order(SortOrder.ascending);
            setColumn26Image(getDescendingImage());
        }
    }

    public void sortByColumn27() {
        setHoldOrder(getColumn27Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn27Order(SortOrder.descending);
            setColumn27Image(getAscendingImage());
        } else {
            setColumn27Order(SortOrder.ascending);
            setColumn27Image(getDescendingImage());
        }
    }

    public void sortByColumn28() {
        setHoldOrder(getColumn28Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn28Order(SortOrder.descending);
            setColumn28Image(getAscendingImage());
        } else {
            setColumn28Order(SortOrder.ascending);
            setColumn28Image(getDescendingImage());
        }
    }

    public void sortByColumn29() {
        setHoldOrder(getColumn29Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn29Order(SortOrder.descending);
            setColumn29Image(getAscendingImage());
        } else {
            setColumn29Order(SortOrder.ascending);
            setColumn29Image(getDescendingImage());
        }
    }

    public void sortByColumn30() {
        setHoldOrder(getColumn30Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn30Order(SortOrder.descending);
            setColumn30Image(getAscendingImage());
        } else {
            setColumn30Order(SortOrder.ascending);
            setColumn30Image(getDescendingImage());
        }
    }

    public void sortByColumn31() {
        setHoldOrder(getColumn31Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn31Order(SortOrder.descending);
            setColumn31Image(getAscendingImage());
        } else {
            setColumn31Order(SortOrder.ascending);
            setColumn31Image(getDescendingImage());
        }
    }

    public void sortByColumn32() {
        setHoldOrder(getColumn32Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn32Order(SortOrder.descending);
            setColumn32Image(getAscendingImage());
        } else {
            setColumn32Order(SortOrder.ascending);
            setColumn32Image(getDescendingImage());
        }
    }

    public void sortByColumn33() {
        setHoldOrder(getColumn33Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn33Order(SortOrder.descending);
            setColumn33Image(getAscendingImage());
        } else {
            setColumn33Order(SortOrder.ascending);
            setColumn33Image(getDescendingImage());
        }
    }

    public void sortByColumn34() {
        setHoldOrder(getColumn34Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn34Order(SortOrder.descending);
            setColumn34Image(getAscendingImage());
        } else {
            setColumn34Order(SortOrder.ascending);
            setColumn34Image(getDescendingImage());
        }
    }

    public void sortByColumn35() {
        setHoldOrder(getColumn35Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn35Order(SortOrder.descending);
            setColumn35Image(getAscendingImage());
        } else {
            setColumn35Order(SortOrder.ascending);
            setColumn35Image(getDescendingImage());
        }
    }

    public void sortByColumn36() {
        setHoldOrder(getColumn36Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn36Order(SortOrder.descending);
            setColumn36Image(getAscendingImage());
        } else {
            setColumn36Order(SortOrder.ascending);
            setColumn36Image(getDescendingImage());
        }
    }

    public void sortByColumn37() {
        setHoldOrder(getColumn37Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn37Order(SortOrder.descending);
            setColumn37Image(getAscendingImage());
        } else {
            setColumn37Order(SortOrder.ascending);
            setColumn37Image(getDescendingImage());
        }
    }

    public void sortByColumn38() {
        setHoldOrder(getColumn38Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn38Order(SortOrder.descending);
            setColumn38Image(getAscendingImage());
        } else {
            setColumn38Order(SortOrder.ascending);
            setColumn38Image(getDescendingImage());
        }
    }

    public void sortByColumn39() {
        setHoldOrder(getColumn39Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn39Order(SortOrder.descending);
            setColumn39Image(getAscendingImage());
        } else {
            setColumn39Order(SortOrder.ascending);
            setColumn39Image(getDescendingImage());
        }
    }

    public void sortByColumn40() {
        setHoldOrder(getColumn40Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn40Order(SortOrder.descending);
            setColumn40Image(getAscendingImage());
        } else {
            setColumn40Order(SortOrder.ascending);
            setColumn40Image(getDescendingImage());
        }
    }

    public void sortByColumn41() {
        setHoldOrder(getColumn41Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn41Order(SortOrder.descending);
            setColumn41Image(getAscendingImage());
        } else {
            setColumn41Order(SortOrder.ascending);
            setColumn41Image(getDescendingImage());
        }
    }

    public void sortByColumn42() {
        setHoldOrder(getColumn42Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn42Order(SortOrder.descending);
            setColumn42Image(getAscendingImage());
        } else {
            setColumn42Order(SortOrder.ascending);
            setColumn42Image(getDescendingImage());
        }
    }

    public void sortByColumn43() {
        setHoldOrder(getColumn43Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn43Order(SortOrder.descending);
            setColumn43Image(getAscendingImage());
        } else {
            setColumn43Order(SortOrder.ascending);
            setColumn43Image(getDescendingImage());
        }
    }

    public void sortByColumn44() {
        setHoldOrder(getColumn44Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn44Order(SortOrder.descending);
            setColumn44Image(getAscendingImage());
        } else {
            setColumn44Order(SortOrder.ascending);
            setColumn44Image(getDescendingImage());
        }
    }

    public void sortByColumn45() {
        setHoldOrder(getColumn45Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn45Order(SortOrder.descending);
            setColumn45Image(getAscendingImage());
        } else {
            setColumn45Order(SortOrder.ascending);
            setColumn45Image(getDescendingImage());
        }
    }

    public void sortByColumn46() {
        setHoldOrder(getColumn46Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn46Order(SortOrder.descending);
            setColumn46Image(getAscendingImage());
        } else {
            setColumn46Order(SortOrder.ascending);
            setColumn46Image(getDescendingImage());
        }
    }

    public void sortByColumn47() {
        setHoldOrder(getColumn47Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn47Order(SortOrder.descending);
            setColumn47Image(getAscendingImage());
        } else {
            setColumn47Order(SortOrder.ascending);
            setColumn47Image(getDescendingImage());
        }
    }

    public void sortByColumn48() {
        setHoldOrder(getColumn48Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn48Order(SortOrder.descending);
            setColumn48Image(getAscendingImage());
        } else {
            setColumn48Order(SortOrder.ascending);
            setColumn48Image(getDescendingImage());
        }
    }

    public void sortByColumn49() {
        setHoldOrder(getColumn49Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn49Order(SortOrder.descending);
            setColumn49Image(getAscendingImage());
        } else {
            setColumn49Order(SortOrder.ascending);
            setColumn49Image(getDescendingImage());
        }
    }

    public void sortByColumn50() {
        setHoldOrder(getColumn50Order());
        resetOrder();
        if (getHoldOrder().equals(SortOrder.ascending)) {
            setColumn50Order(SortOrder.descending);
            setColumn50Image(getAscendingImage());
        } else {
            setColumn50Order(SortOrder.ascending);
            setColumn50Image(getDescendingImage());
        }
    }

    /**
     * @return the holdOrder
     */
    public SortOrder getHoldOrder() {
        return holdOrder;
    }

    /**
     * @param holdOrder the holdOrder to set
     */
    public void setHoldOrder(SortOrder holdOrder) {
        this.holdOrder = holdOrder;
    }

    /**
     * @return the unsortedImage
     */
    public String getUnsortedImage() {
        return unsortedImage;
    }

    /**
     * @param unsortedImage the unsortedImage to set
     */
    public void setUnsortedImage(String unsortedImage) {
        this.unsortedImage = unsortedImage;
    }

    /**
     * @return the ascendingImage
     */
    public String getAscendingImage() {
        return ascendingImage;
    }

    /**
     * @param ascendingImage the ascendingImage to set
     */
    public void setAscendingImage(String ascendingImage) {
        this.ascendingImage = ascendingImage;
    }

    /**
     * @return the descendingImage
     */
    public String getDescendingImage() {
        return descendingImage;
    }

    /**
     * @param descendingImage the descendingImage to set
     */
    public void setDescendingImage(String descendingImage) {
        this.descendingImage = descendingImage;
    }
    
    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    /**
     * @return the column1Order
     */
    public SortOrder getColumn1Order() {
        return column1Order;
    }

    /**
     * @param column1Order the column1Order to set
     */
    public void setColumn1Order(SortOrder column1Order) {
        this.column1Order = column1Order;
    }

    /**
     * @return the column2Order
     */
    public SortOrder getColumn2Order() {
        return column2Order;
    }

    /**
     * @param column2Order the column2Order to set
     */
    public void setColumn2Order(SortOrder column2Order) {
        this.column2Order = column2Order;
    }

    /**
     * @return the column3Order
     */
    public SortOrder getColumn3Order() {
        return column3Order;
    }

    /**
     * @param column3Order the column3Order to set
     */
    public void setColumn3Order(SortOrder column3Order) {
        this.column3Order = column3Order;
    }

    /**
     * @return the column4Order
     */
    public SortOrder getColumn4Order() {
        return column4Order;
    }

    /**
     * @param column4Order the column4Order to set
     */
    public void setColumn4Order(SortOrder column4Order) {
        this.column4Order = column4Order;
    }

    /**
     * @return the column5Order
     */
    public SortOrder getColumn5Order() {
        return column5Order;
    }

    /**
     * @param column5Order the column5Order to set
     */
    public void setColumn5Order(SortOrder column5Order) {
        this.column5Order = column5Order;
    }

    /**
     * @return the column6Order
     */
    public SortOrder getColumn6Order() {
        return column6Order;
    }

    /**
     * @param column6Order the column6Order to set
     */
    public void setColumn6Order(SortOrder column6Order) {
        this.column6Order = column6Order;
    }

    /**
     * @return the column7Order
     */
    public SortOrder getColumn7Order() {
        return column7Order;
    }

    /**
     * @param column7Order the column7Order to set
     */
    public void setColumn7Order(SortOrder column7Order) {
        this.column7Order = column7Order;
    }

    /**
     * @return the column8Order
     */
    public SortOrder getColumn8Order() {
        return column8Order;
    }

    /**
     * @param column8Order the column8Order to set
     */
    public void setColumn8Order(SortOrder column8Order) {
        this.column8Order = column8Order;
    }

    /**
     * @return the column9Order
     */
    public SortOrder getColumn9Order() {
        return column9Order;
    }

    /**
     * @param column9Order the column9Order to set
     */
    public void setColumn9Order(SortOrder column9Order) {
        this.column9Order = column9Order;
    }

    /**
     * @return the column10Order
     */
    public SortOrder getColumn10Order() {
        return column10Order;
    }

    /**
     * @param column10Order the column10Order to set
     */
    public void setColumn10Order(SortOrder column10Order) {
        this.column10Order = column10Order;
    }

    /**
     * @return the column11Order
     */
    public SortOrder getColumn11Order() {
        return column11Order;
    }

    /**
     * @param column11Order the column11Order to set
     */
    public void setColumn11Order(SortOrder column11Order) {
        this.column11Order = column11Order;
    }

    /**
     * @return the column12Order
     */
    public SortOrder getColumn12Order() {
        return column12Order;
    }

    /**
     * @param column12Order the column12Order to set
     */
    public void setColumn12Order(SortOrder column12Order) {
        this.column12Order = column12Order;
    }

    /**
     * @return the column1Image
     */
    public String getColumn1Image() {
        return column1Image;
    }

    /**
     * @param column1Image the column1Image to set
     */
    public void setColumn1Image(String column1Image) {
        this.column1Image = column1Image;
    }

    /**
     * @return the column2Image
     */
    public String getColumn2Image() {
        return column2Image;
    }

    /**
     * @param column2Image the column2Image to set
     */
    public void setColumn2Image(String column2Image) {
        this.column2Image = column2Image;
    }

    /**
     * @return the column3Image
     */
    public String getColumn3Image() {
        return column3Image;
    }

    /**
     * @param column3Image the column3Image to set
     */
    public void setColumn3Image(String column3Image) {
        this.column3Image = column3Image;
    }

    /**
     * @return the column4Image
     */
    public String getColumn4Image() {
        return column4Image;
    }

    /**
     * @param column4Image the column4Image to set
     */
    public void setColumn4Image(String column4Image) {
        this.column4Image = column4Image;
    }

    /**
     * @return the column5Image
     */
    public String getColumn5Image() {
        return column5Image;
    }

    /**
     * @param column5Image the column5Image to set
     */
    public void setColumn5Image(String column5Image) {
        this.column5Image = column5Image;
    }

    /**
     * @return the column6Image
     */
    public String getColumn6Image() {
        return column6Image;
    }

    /**
     * @param column6Image the column6Image to set
     */
    public void setColumn6Image(String column6Image) {
        this.column6Image = column6Image;
    }

    /**
     * @return the column7Image
     */
    public String getColumn7Image() {
        return column7Image;
    }

    /**
     * @param column7Image the column7Image to set
     */
    public void setColumn7Image(String column7Image) {
        this.column7Image = column7Image;
    }

    /**
     * @return the column8Image
     */
    public String getColumn8Image() {
        return column8Image;
    }

    /**
     * @param column8Image the column8Image to set
     */
    public void setColumn8Image(String column8Image) {
        this.column8Image = column8Image;
    }

    /**
     * @return the column9Image
     */
    public String getColumn9Image() {
        return column9Image;
    }

    /**
     * @param column9Image the column9Image to set
     */
    public void setColumn9Image(String column9Image) {
        this.column9Image = column9Image;
    }

    /**
     * @return the column10Image
     */
    public String getColumn10Image() {
        return column10Image;
    }

    /**
     * @param column10Image the column10Image to set
     */
    public void setColumn10Image(String column10Image) {
        this.column10Image = column10Image;
    }

    /**
     * @return the column11Image
     */
    public String getColumn11Image() {
        return column11Image;
    }

    /**
     * @param column11Image the column11Image to set
     */
    public void setColumn11Image(String column11Image) {
        this.column11Image = column11Image;
    }

    /**
     * @return the column12Image
     */
    public String getColumn12Image() {
        return column12Image;
    }

    /**
     * @param column12Image the column12Image to set
     */
    public void setColumn12Image(String column12Image) {
        this.column12Image = column12Image;
    }

    /**
     * @return the column13Order
     */
    public SortOrder getColumn13Order() {
        return column13Order;
    }

    /**
     * @param column13Order the column13Order to set
     */
    public void setColumn13Order(SortOrder column13Order) {
        this.column13Order = column13Order;
    }

    /**
     * @return the column14Order
     */
    public SortOrder getColumn14Order() {
        return column14Order;
    }

    /**
     * @param column14Order the column14Order to set
     */
    public void setColumn14Order(SortOrder column14Order) {
        this.column14Order = column14Order;
    }

    /**
     * @return the column15Order
     */
    public SortOrder getColumn15Order() {
        return column15Order;
    }

    /**
     * @param column15Order the column15Order to set
     */
    public void setColumn15Order(SortOrder column15Order) {
        this.column15Order = column15Order;
    }

    /**
     * @return the column16Order
     */
    public SortOrder getColumn16Order() {
        return column16Order;
    }

    /**
     * @param column16Order the column16Order to set
     */
    public void setColumn16Order(SortOrder column16Order) {
        this.column16Order = column16Order;
    }

    /**
     * @return the column17Order
     */
    public SortOrder getColumn17Order() {
        return column17Order;
    }

    /**
     * @param column17Order the column17Order to set
     */
    public void setColumn17Order(SortOrder column17Order) {
        this.column17Order = column17Order;
    }

    /**
     * @return the column18Order
     */
    public SortOrder getColumn18Order() {
        return column18Order;
    }

    /**
     * @param column18Order the column18Order to set
     */
    public void setColumn18Order(SortOrder column18Order) {
        this.column18Order = column18Order;
    }

    /**
     * @return the column19Order
     */
    public SortOrder getColumn19Order() {
        return column19Order;
    }

    /**
     * @param column19Order the column19Order to set
     */
    public void setColumn19Order(SortOrder column19Order) {
        this.column19Order = column19Order;
    }

    /**
     * @return the column20Order
     */
    public SortOrder getColumn20Order() {
        return column20Order;
    }

    /**
     * @param column20Order the column20Order to set
     */
    public void setColumn20Order(SortOrder column20Order) {
        this.column20Order = column20Order;
    }

    /**
     * @return the column21Order
     */
    public SortOrder getColumn21Order() {
        return column21Order;
    }

    /**
     * @param column21Order the column21Order to set
     */
    public void setColumn21Order(SortOrder column21Order) {
        this.column21Order = column21Order;
    }

    /**
     * @return the column22Order
     */
    public SortOrder getColumn22Order() {
        return column22Order;
    }

    /**
     * @param column22Order the column22Order to set
     */
    public void setColumn22Order(SortOrder column22Order) {
        this.column22Order = column22Order;
    }

    /**
     * @return the column23Order
     */
    public SortOrder getColumn23Order() {
        return column23Order;
    }

    /**
     * @param column23Order the column23Order to set
     */
    public void setColumn23Order(SortOrder column23Order) {
        this.column23Order = column23Order;
    }

    /**
     * @return the column24Order
     */
    public SortOrder getColumn24Order() {
        return column24Order;
    }

    /**
     * @param column24Order the column24Order to set
     */
    public void setColumn24Order(SortOrder column24Order) {
        this.column24Order = column24Order;
    }

    /**
     * @return the column25Order
     */
    public SortOrder getColumn25Order() {
        return column25Order;
    }

    /**
     * @param column25Order the column25Order to set
     */
    public void setColumn25Order(SortOrder column25Order) {
        this.column25Order = column25Order;
    }

    /**
     * @return the column26Order
     */
    public SortOrder getColumn26Order() {
        return column26Order;
    }

    /**
     * @param column26Order the column26Order to set
     */
    public void setColumn26Order(SortOrder column26Order) {
        this.column26Order = column26Order;
    }

    /**
     * @return the column27Order
     */
    public SortOrder getColumn27Order() {
        return column27Order;
    }

    /**
     * @param column27Order the column27Order to set
     */
    public void setColumn27Order(SortOrder column27Order) {
        this.column27Order = column27Order;
    }

    /**
     * @return the column28Order
     */
    public SortOrder getColumn28Order() {
        return column28Order;
    }

    /**
     * @param column28Order the column28Order to set
     */
    public void setColumn28Order(SortOrder column28Order) {
        this.column28Order = column28Order;
    }

    /**
     * @return the column29Order
     */
    public SortOrder getColumn29Order() {
        return column29Order;
    }

    /**
     * @param column29Order the column29Order to set
     */
    public void setColumn29Order(SortOrder column29Order) {
        this.column29Order = column29Order;
    }

    /**
     * @return the column13Image
     */
    public String getColumn13Image() {
        return column13Image;
    }

    /**
     * @param column13Image the column13Image to set
     */
    public void setColumn13Image(String column13Image) {
        this.column13Image = column13Image;
    }

    /**
     * @return the column14Image
     */
    public String getColumn14Image() {
        return column14Image;
    }

    /**
     * @param column14Image the column14Image to set
     */
    public void setColumn14Image(String column14Image) {
        this.column14Image = column14Image;
    }

    /**
     * @return the column15Image
     */
    public String getColumn15Image() {
        return column15Image;
    }

    /**
     * @param column15Image the column15Image to set
     */
    public void setColumn15Image(String column15Image) {
        this.column15Image = column15Image;
    }

    /**
     * @return the column16Image
     */
    public String getColumn16Image() {
        return column16Image;
    }

    /**
     * @param column16Image the column16Image to set
     */
    public void setColumn16Image(String column16Image) {
        this.column16Image = column16Image;
    }

    /**
     * @return the column17Image
     */
    public String getColumn17Image() {
        return column17Image;
    }

    /**
     * @param column17Image the column17Image to set
     */
    public void setColumn17Image(String column17Image) {
        this.column17Image = column17Image;
    }

    /**
     * @return the column18Image
     */
    public String getColumn18Image() {
        return column18Image;
    }

    /**
     * @param column18Image the column18Image to set
     */
    public void setColumn18Image(String column18Image) {
        this.column18Image = column18Image;
    }

    /**
     * @return the column19Image
     */
    public String getColumn19Image() {
        return column19Image;
    }

    /**
     * @param column19Image the column19Image to set
     */
    public void setColumn19Image(String column19Image) {
        this.column19Image = column19Image;
    }

    /**
     * @return the column20Image
     */
    public String getColumn20Image() {
        return column20Image;
    }

    /**
     * @param column20Image the column20Image to set
     */
    public void setColumn20Image(String column20Image) {
        this.column20Image = column20Image;
    }

    /**
     * @return the column21Image
     */
    public String getColumn21Image() {
        return column21Image;
    }

    /**
     * @param column21Image the column21Image to set
     */
    public void setColumn21Image(String column21Image) {
        this.column21Image = column21Image;
    }

    /**
     * @return the column22Image
     */
    public String getColumn22Image() {
        return column22Image;
    }

    /**
     * @param column22Image the column22Image to set
     */
    public void setColumn22Image(String column22Image) {
        this.column22Image = column22Image;
    }

    /**
     * @return the column23Image
     */
    public String getColumn23Image() {
        return column23Image;
    }

    /**
     * @param column23Image the column23Image to set
     */
    public void setColumn23Image(String column23Image) {
        this.column23Image = column23Image;
    }

    /**
     * @return the column24Image
     */
    public String getColumn24Image() {
        return column24Image;
    }

    /**
     * @param column24Image the column24Image to set
     */
    public void setColumn24Image(String column24Image) {
        this.column24Image = column24Image;
    }

    /**
     * @return the column25Image
     */
    public String getColumn25Image() {
        return column25Image;
    }

    /**
     * @param column25Image the column25Image to set
     */
    public void setColumn25Image(String column25Image) {
        this.column25Image = column25Image;
    }

    /**
     * @return the column26Image
     */
    public String getColumn26Image() {
        return column26Image;
    }

    /**
     * @param column26Image the column26Image to set
     */
    public void setColumn26Image(String column26Image) {
        this.column26Image = column26Image;
    }

    /**
     * @return the column27Image
     */
    public String getColumn27Image() {
        return column27Image;
    }

    /**
     * @param column27Image the column27Image to set
     */
    public void setColumn27Image(String column27Image) {
        this.column27Image = column27Image;
    }

    /**
     * @return the column28Image
     */
    public String getColumn28Image() {
        return column28Image;
    }

    /**
     * @param column28Image the column28Image to set
     */
    public void setColumn28Image(String column28Image) {
        this.column28Image = column28Image;
    }

    /**
     * @return the column29Image
     */
    public String getColumn29Image() {
        return column29Image;
    }

    /**
     * @param column29Image the column29Image to set
     */
    public void setColumn29Image(String column29Image) {
        this.column29Image = column29Image;
    }

    /**
     * @return the column30Order
     */
    public SortOrder getColumn30Order() {
        return column30Order;
    }

    /**
     * @param column30Order the column30Order to set
     */
    public void setColumn30Order(SortOrder column30Order) {
        this.column30Order = column30Order;
    }

    /**
     * @return the column31Order
     */
    public SortOrder getColumn31Order() {
        return column31Order;
    }

    /**
     * @param column31Order the column31Order to set
     */
    public void setColumn31Order(SortOrder column31Order) {
        this.column31Order = column31Order;
    }

    /**
     * @return the column32Order
     */
    public SortOrder getColumn32Order() {
        return column32Order;
    }

    /**
     * @param column32Order the column32Order to set
     */
    public void setColumn32Order(SortOrder column32Order) {
        this.column32Order = column32Order;
    }

    /**
     * @return the column33Order
     */
    public SortOrder getColumn33Order() {
        return column33Order;
    }

    /**
     * @param column33Order the column33Order to set
     */
    public void setColumn33Order(SortOrder column33Order) {
        this.column33Order = column33Order;
    }

    /**
     * @return the column34Order
     */
    public SortOrder getColumn34Order() {
        return column34Order;
    }

    /**
     * @param column34Order the column34Order to set
     */
    public void setColumn34Order(SortOrder column34Order) {
        this.column34Order = column34Order;
    }

    /**
     * @return the column35Order
     */
    public SortOrder getColumn35Order() {
        return column35Order;
    }

    /**
     * @param column35Order the column35Order to set
     */
    public void setColumn35Order(SortOrder column35Order) {
        this.column35Order = column35Order;
    }

    /**
     * @return the column36Order
     */
    public SortOrder getColumn36Order() {
        return column36Order;
    }

    /**
     * @param column36Order the column36Order to set
     */
    public void setColumn36Order(SortOrder column36Order) {
        this.column36Order = column36Order;
    }

    /**
     * @return the column37Order
     */
    public SortOrder getColumn37Order() {
        return column37Order;
    }

    /**
     * @param column37Order the column37Order to set
     */
    public void setColumn37Order(SortOrder column37Order) {
        this.column37Order = column37Order;
    }

    /**
     * @return the column38Order
     */
    public SortOrder getColumn38Order() {
        return column38Order;
    }

    /**
     * @param column38Order the column38Order to set
     */
    public void setColumn38Order(SortOrder column38Order) {
        this.column38Order = column38Order;
    }

    /**
     * @return the column39Order
     */
    public SortOrder getColumn39Order() {
        return column39Order;
    }

    /**
     * @param column39Order the column39Order to set
     */
    public void setColumn39Order(SortOrder column39Order) {
        this.column39Order = column39Order;
    }

    /**
     * @return the column30Image
     */
    public String getColumn30Image() {
        return column30Image;
    }

    /**
     * @param column30Image the column30Image to set
     */
    public void setColumn30Image(String column30Image) {
        this.column30Image = column30Image;
    }

    /**
     * @return the column31Image
     */
    public String getColumn31Image() {
        return column31Image;
    }

    /**
     * @param column31Image the column31Image to set
     */
    public void setColumn31Image(String column31Image) {
        this.column31Image = column31Image;
    }

    /**
     * @return the column32Image
     */
    public String getColumn32Image() {
        return column32Image;
    }

    /**
     * @param column32Image the column32Image to set
     */
    public void setColumn32Image(String column32Image) {
        this.column32Image = column32Image;
    }

    /**
     * @return the column33Image
     */
    public String getColumn33Image() {
        return column33Image;
    }

    /**
     * @param column33Image the column33Image to set
     */
    public void setColumn33Image(String column33Image) {
        this.column33Image = column33Image;
    }

    /**
     * @return the column34Image
     */
    public String getColumn34Image() {
        return column34Image;
    }

    /**
     * @param column34Image the column34Image to set
     */
    public void setColumn34Image(String column34Image) {
        this.column34Image = column34Image;
    }

    /**
     * @return the column35Image
     */
    public String getColumn35Image() {
        return column35Image;
    }

    /**
     * @param column35Image the column35Image to set
     */
    public void setColumn35Image(String column35Image) {
        this.column35Image = column35Image;
    }

    /**
     * @return the column36Image
     */
    public String getColumn36Image() {
        return column36Image;
    }

    /**
     * @param column36Image the column36Image to set
     */
    public void setColumn36Image(String column36Image) {
        this.column36Image = column36Image;
    }

    /**
     * @return the column37Image
     */
    public String getColumn37Image() {
        return column37Image;
    }

    /**
     * @param column37Image the column37Image to set
     */
    public void setColumn37Image(String column37Image) {
        this.column37Image = column37Image;
    }

    /**
     * @return the column38Image
     */
    public String getColumn38Image() {
        return column38Image;
    }

    /**
     * @param column38Image the column38Image to set
     */
    public void setColumn38Image(String column38Image) {
        this.column38Image = column38Image;
    }

    /**
     * @return the column39Image
     */
    public String getColumn39Image() {
        return column39Image;
    }

    /**
     * @param column39Image the column39Image to set
     */
    public void setColumn39Image(String column39Image) {
        this.column39Image = column39Image;
    }

    /**
     * @return the column40Order
     */
    public SortOrder getColumn40Order() {
        return column40Order;
    }

    /**
     * @param column40Order the column40Order to set
     */
    public void setColumn40Order(SortOrder column40Order) {
        this.column40Order = column40Order;
    }

    /**
     * @return the column41Order
     */
    public SortOrder getColumn41Order() {
        return column41Order;
    }

    /**
     * @param column41Order the column41Order to set
     */
    public void setColumn41Order(SortOrder column41Order) {
        this.column41Order = column41Order;
    }

    /**
     * @return the column42Order
     */
    public SortOrder getColumn42Order() {
        return column42Order;
    }

    /**
     * @param column42Order the column42Order to set
     */
    public void setColumn42Order(SortOrder column42Order) {
        this.column42Order = column42Order;
    }

    /**
     * @return the column43Order
     */
    public SortOrder getColumn43Order() {
        return column43Order;
    }

    /**
     * @param column43Order the column43Order to set
     */
    public void setColumn43Order(SortOrder column43Order) {
        this.column43Order = column43Order;
    }

    /**
     * @return the column44Order
     */
    public SortOrder getColumn44Order() {
        return column44Order;
    }

    /**
     * @param column44Order the column44Order to set
     */
    public void setColumn44Order(SortOrder column44Order) {
        this.column44Order = column44Order;
    }

    /**
     * @return the column45Order
     */
    public SortOrder getColumn45Order() {
        return column45Order;
    }

    /**
     * @param column45Order the column45Order to set
     */
    public void setColumn45Order(SortOrder column45Order) {
        this.column45Order = column45Order;
    }

    /**
     * @return the column46Order
     */
    public SortOrder getColumn46Order() {
        return column46Order;
    }

    /**
     * @param column46Order the column46Order to set
     */
    public void setColumn46Order(SortOrder column46Order) {
        this.column46Order = column46Order;
    }

    /**
     * @return the column47Order
     */
    public SortOrder getColumn47Order() {
        return column47Order;
    }

    /**
     * @param column47Order the column47Order to set
     */
    public void setColumn47Order(SortOrder column47Order) {
        this.column47Order = column47Order;
    }

    /**
     * @return the column48Order
     */
    public SortOrder getColumn48Order() {
        return column48Order;
    }

    /**
     * @param column48Order the column48Order to set
     */
    public void setColumn48Order(SortOrder column48Order) {
        this.column48Order = column48Order;
    }

    /**
     * @return the column49Order
     */
    public SortOrder getColumn49Order() {
        return column49Order;
    }

    /**
     * @param column49Order the column49Order to set
     */
    public void setColumn49Order(SortOrder column49Order) {
        this.column49Order = column49Order;
    }

    /**
     * @return the column50Order
     */
    public SortOrder getColumn50Order() {
        return column50Order;
    }

    /**
     * @param column50Order the column50Order to set
     */
    public void setColumn50Order(SortOrder column50Order) {
        this.column50Order = column50Order;
    }

    /**
     * @return the column40Image
     */
    public String getColumn40Image() {
        return column40Image;
    }

    /**
     * @param column40Image the column40Image to set
     */
    public void setColumn40Image(String column40Image) {
        this.column40Image = column40Image;
    }

    /**
     * @return the column41Image
     */
    public String getColumn41Image() {
        return column41Image;
    }

    /**
     * @param column41Image the column41Image to set
     */
    public void setColumn41Image(String column41Image) {
        this.column41Image = column41Image;
    }

    /**
     * @return the column42Image
     */
    public String getColumn42Image() {
        return column42Image;
    }

    /**
     * @param column42Image the column42Image to set
     */
    public void setColumn42Image(String column42Image) {
        this.column42Image = column42Image;
    }

    /**
     * @return the column43Image
     */
    public String getColumn43Image() {
        return column43Image;
    }

    /**
     * @param column43Image the column43Image to set
     */
    public void setColumn43Image(String column43Image) {
        this.column43Image = column43Image;
    }

    /**
     * @return the column44Image
     */
    public String getColumn44Image() {
        return column44Image;
    }

    /**
     * @param column44Image the column44Image to set
     */
    public void setColumn44Image(String column44Image) {
        this.column44Image = column44Image;
    }

    /**
     * @return the column45Image
     */
    public String getColumn45Image() {
        return column45Image;
    }

    /**
     * @param column45Image the column45Image to set
     */
    public void setColumn45Image(String column45Image) {
        this.column45Image = column45Image;
    }

    /**
     * @return the column46Image
     */
    public String getColumn46Image() {
        return column46Image;
    }

    /**
     * @param column46Image the column46Image to set
     */
    public void setColumn46Image(String column46Image) {
        this.column46Image = column46Image;
    }

    /**
     * @return the column47Image
     */
    public String getColumn47Image() {
        return column47Image;
    }

    /**
     * @param column47Image the column47Image to set
     */
    public void setColumn47Image(String column47Image) {
        this.column47Image = column47Image;
    }

    /**
     * @return the column48Image
     */
    public String getColumn48Image() {
        return column48Image;
    }

    /**
     * @param column48Image the column48Image to set
     */
    public void setColumn48Image(String column48Image) {
        this.column48Image = column48Image;
    }

    /**
     * @return the column49Image
     */
    public String getColumn49Image() {
        return column49Image;
    }

    /**
     * @param column49Image the column49Image to set
     */
    public void setColumn49Image(String column49Image) {
        this.column49Image = column49Image;
    }

    /**
     * @return the column50Image
     */
    public String getColumn50Image() {
        return column50Image;
    }

    /**
     * @param column50Image the column50Image to set
     */
    public void setColumn50Image(String column50Image) {
        this.column50Image = column50Image;
    }
}
