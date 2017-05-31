package template;

/**
 * Created by guojun.wang on 2017/3/30.
 */
public class BaseDataDto {
    private String version;
    private String[][] jobType;
    private String[][] subJobType;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String[][] getJobType() {
        return jobType;
    }

    public void setJobType(String[][] jobType) {
        this.jobType = jobType;
    }

    public String[][] getSubJobType() {
        return subJobType;
    }

    public void setSubJobType(String[][] subJobType) {
        this.subJobType = subJobType;
    }
}
