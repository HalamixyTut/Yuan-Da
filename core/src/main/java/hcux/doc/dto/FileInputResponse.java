package hcux.doc.dto;

import java.util.ArrayList;
import java.util.List;

public class FileInputResponse {
    private String error;
    private List<String> initialPreview = new ArrayList<>();
    private List<InitialPreviewConfig> initialPreviewConfig = new ArrayList<>();
    private List<InitialPreviewConfig> initialPreviewThumbTags = new ArrayList<>();
    private boolean append;
    private Long attachmentId;
    private Long fileId; //文件ID
    private String fileName;
    private String coverFlag;

    public FileInputResponse() {
    }

    public FileInputResponse(boolean append) {
        this.append = append;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getInitialPreview() {
        return initialPreview;
    }

    public void setInitialPreview(List<String> initialPreview) {
        this.initialPreview = initialPreview;
    }

    public List<InitialPreviewConfig> getInitialPreviewConfig() {
        return initialPreviewConfig;
    }

    public void setInitialPreviewConfig(List<InitialPreviewConfig> initialPreviewConfig) {
        this.initialPreviewConfig = initialPreviewConfig;
    }

    public List<InitialPreviewConfig> getInitialPreviewThumbTags() {
        return initialPreviewThumbTags;
    }

    public void setInitialPreviewThumbTags(List<InitialPreviewConfig> initialPreviewThumbTags) {
        this.initialPreviewThumbTags = initialPreviewThumbTags;
    }

    public boolean isAppend() {
        return append;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCoverFlag() {
        return coverFlag;
    }

    public void setCoverFlag(String coverFlag) {
        this.coverFlag = coverFlag;
    }
}
