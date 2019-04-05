package com.ttn.linksharing.DTO;

import com.ttn.linksharing.entities.Resource;
import com.ttn.linksharing.enums.Seriousness;
import com.ttn.linksharing.enums.Visibility;

import java.util.List;

public class TopicDTO {
    private Long id;
    private String name;
    private Seriousness seriousness;
    private Visibility visibility;
    private String creator;
    private Integer subsCount;
    private List<Resource> resourcesList;
    private String creatorProfilePic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Seriousness getSeriousness() {
        return seriousness;
    }

    public void setSeriousness(Seriousness seriousness) {
        this.seriousness = seriousness;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getSubsCount() {
        return subsCount;
    }

    public void setSubsCount(Integer subsCount) {
        this.subsCount = subsCount;
    }

    public List<Resource> getResourcesList() {
        return resourcesList;
    }

    public void setResourcesList(List<Resource> resourcesList) {
        this.resourcesList = resourcesList;
    }

    public String getCreatorProfilePic() {
        return creatorProfilePic;
    }

    public void setCreatorProfilePic(String creatorProfilePic) {
        this.creatorProfilePic = creatorProfilePic;
    }
}
