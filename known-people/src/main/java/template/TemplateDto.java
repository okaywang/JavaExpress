package template;

/**
 * Created by guojun.wang on 2017/3/31.
 */
public class TemplateDto {
    private long templateId;
    private String templateName;
    private String templateTypeName;
    //private TemplateModuleEnum module;
    private int module;
    private String templateContent;
    private long createUserId;
    //private TemplateOwnerEnum templateOwner;
    private int templateOwner;
    private boolean hot;

    public long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateTypeName() {
        return templateTypeName;
    }

    public void setTemplateTypeName(String templateTypeName) {
        this.templateTypeName = templateTypeName;
    }

    public int getModule() {
        return module;
    }

    public void setModule(int module) {
        this.module = module;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }

    public int getTemplateOwner() {
        return templateOwner;
    }

    public void setTemplateOwner(int templateOwner) {
        this.templateOwner = templateOwner;
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }
}
