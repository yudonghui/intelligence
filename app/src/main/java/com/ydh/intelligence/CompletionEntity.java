package com.ydh.intelligence;

import java.util.List;

/**
 * Date:2023/2/6
 * Time:13:57
 * author:ydh
 */
public class CompletionEntity {

    /**
     * id : cmpl-6gomcdDyM9hMaq2VPNWWPVBTIvvnd
     * object : text_completion
     * created : 1675663006
     * model : text-davinci-003
     * choices : [{"text":"\n\n刘强东，男，1974年出生，江苏省苏州市人，中国企业家、投资家。毕业于清华大学计算机系，获得工","index":0,"logprobs":null,"finish_reason":"length"}]
     * usage : {"prompt_tokens":11,"completion_tokens":99,"total_tokens":110}
     */

    private String id;
    private String object;
    private String created;
    private String model;
    private UsageBean usage;
    private List<ChoicesBean> choices;
    private ErrorBean error;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public UsageBean getUsage() {
        return usage;
    }

    public void setUsage(UsageBean usage) {
        this.usage = usage;
    }

    public List<ChoicesBean> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoicesBean> choices) {
        this.choices = choices;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public static class UsageBean {
        /**
         * prompt_tokens : 11
         * completion_tokens : 99
         * total_tokens : 110
         */

        private String prompt_tokens;
        private String completion_tokens;
        private String total_tokens;

        public String getPrompt_tokens() {
            return prompt_tokens;
        }

        public void setPrompt_tokens(String prompt_tokens) {
            this.prompt_tokens = prompt_tokens;
        }

        public String getCompletion_tokens() {
            return completion_tokens;
        }

        public void setCompletion_tokens(String completion_tokens) {
            this.completion_tokens = completion_tokens;
        }

        public String getTotal_tokens() {
            return total_tokens;
        }

        public void setTotal_tokens(String total_tokens) {
            this.total_tokens = total_tokens;
        }
    }

    public static class ChoicesBean {
        /**
         * text :
         * <p>
         * 刘强东，男，1974年出生，江苏省苏州市人，中国企业家、投资家。毕业于清华大学计算机系，获得工
         * index : 0
         * logprobs : null
         * finish_reason : length
         */

        private String text;
        private String index;
        private Object logprobs;
        private String finish_reason;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public Object getLogprobs() {
            return logprobs;
        }

        public void setLogprobs(Object logprobs) {
            this.logprobs = logprobs;
        }

        public String getFinish_reason() {
            return finish_reason;
        }

        public void setFinish_reason(String finish_reason) {
            this.finish_reason = finish_reason;
        }
    }

    public static class ErrorBean {
        private String message;
        private String type;
        private String param;
        private String code;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
