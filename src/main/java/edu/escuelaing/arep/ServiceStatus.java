package edu.escuelaing.arep;

public class ServiceStatus {
    private String url;
    private boolean active;


    public ServiceStatus() {
    }

    public ServiceStatus(String url, boolean active) {
        this.url = url;
        this.active = active;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public String toString() {
        return "{" +
            " url='" + getUrl() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }

}
