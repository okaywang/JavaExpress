package template;

/**
 * Created by guojun.wang on 2017/3/31.
 */
public class JobTemplateAddDto {

    private String jobtitle;//职位标题

    private String jobTypeMain;//    大类id

    private String subJobTypeMain;//   小类id

    private String monthsalary;//薪资

    private int positionnature;//职位性质  2、全职 1、兼职 4、实习 5 校园


    private String workplace;//工作地点
    private int provinceid;//省份编号
    private String cityid;//城市编号
    private int cqid;//城区编号
    private int quantity;//招聘人数


    private String mineducationlevel;//教育最小程度


    private String minyears;//最小工作经验


    private String jobdescription;//职位描述
    private String benefit;//职位福利标签
    private int enddate;//发布时长

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getJobTypeMain() {
        return jobTypeMain;
    }

    public void setJobTypeMain(String jobTypeMain) {
        this.jobTypeMain = jobTypeMain;
    }

    public String getSubJobTypeMain() {
        return subJobTypeMain;
    }

    public void setSubJobTypeMain(String subJobTypeMain) {
        this.subJobTypeMain = subJobTypeMain;
    }

    public String getMonthsalary() {
        return monthsalary;
    }

    public void setMonthsalary(String monthsalary) {
        this.monthsalary = monthsalary;
    }

    public int getPositionnature() {
        return positionnature;
    }

    public void setPositionnature(int positionnature) {
        this.positionnature = positionnature;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public int getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(int provinceid) {
        this.provinceid = provinceid;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public int getCqid() {
        return cqid;
    }

    public void setCqid(int cqid) {
        this.cqid = cqid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMineducationlevel() {
        return mineducationlevel;
    }

    public void setMineducationlevel(String mineducationlevel) {
        this.mineducationlevel = mineducationlevel;
    }

    public String getMinyears() {
        return minyears;
    }

    public void setMinyears(String minyears) {
        this.minyears = minyears;
    }

    public String getJobdescription() {
        return jobdescription;
    }

    public void setJobdescription(String jobdescription) {
        this.jobdescription = jobdescription;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public int getEnddate() {
        return enddate;
    }

    public void setEnddate(int enddate) {
        this.enddate = enddate;
    }
}
