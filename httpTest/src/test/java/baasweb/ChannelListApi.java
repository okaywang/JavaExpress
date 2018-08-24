package baasweb;

import java.util.Date;

/**
 * Created by wangguojun01 on 2018/8/9.
 */
public class ChannelListApi extends HttpApiBase {
    EnvBase env = new EnvLocal();

    @Override
    String getUrl() {
        return env.getUrl("/baaschannel/list");
    }

    @Override
    Class<?> getResponseType() {
        return ChannelListItem[].class;
    }

    static class ChannelListItem {
        private String channelName;
        private String description;
        private int height;
        private Date createTime;
        private Date updateTime;

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }

        @Override
        public String toString() {
            return "ChannelListItem{" +
                    "channelName='" + channelName + '\'' +
                    ", description='" + description + '\'' +
                    ", height=" + height +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    '}';
        }
    }
}
