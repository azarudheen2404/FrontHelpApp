package in.esquareinfo.web.app;

public class District {
    private String disId;
    private String disName;

    public District(String id, String name) {
        this.disId = id;
        this.disName = name;
    }

    public String getDisName() {
        return this.disName;
    }

    public void setDisName(String disName) {
        this.disName = disName;
    }

    public String getDisId() {
        return this.disId;
    }

    public void setDisId(String disId) {
        this.disId = disId;
    }

    public String toString() {
        return this.disName;
    }
}
