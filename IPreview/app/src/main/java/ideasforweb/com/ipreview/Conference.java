package ideasforweb.com.ipreview;

import java.net.URL;
import java.util.Date;
import java.util.List;

public class Conference {

    private String idConference;
    private String nameEvent;
    private String locationEvent;
    private Date dateTimeEvent;
    private URL urlImage;
    private List<UrlPDFs> urlPDFs;

    public String getIdConference() {
        return idConference;
    }

    public void setIdConference(String idConference) {
        this.idConference = idConference;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getLocationEvent() {
        return locationEvent;
    }

    public void setLocationEvent(String locationEvent) {
        this.locationEvent = locationEvent;
    }

    public Date getDateTimeEvent() {
        return dateTimeEvent;
    }

    public void setDateTimeEvent(Date dateTimeEvent) {
        this.dateTimeEvent = dateTimeEvent;
    }

    public URL getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(URL urlImage) {
        this.urlImage = urlImage;
    }

    public List<UrlPDFs> getUrlPDFs() {
        return urlPDFs;
    }

    public void setUrlPDFs(List<UrlPDFs> urlPDFs) {
        this.urlPDFs = urlPDFs;
    }


}
