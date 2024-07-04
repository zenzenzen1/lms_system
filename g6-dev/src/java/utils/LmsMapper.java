package utils;

import dto.request.SettingRequest;
import entity.SettingDetails;

public class LmsMapper {
    public static SettingRequest mapToSettingRequest(SettingDetails details) {
        SettingRequest request = new SettingRequest();

        request.setId(details.getId());
        request.setSettingName(details.getSettingName());
        request.setSettingValue(details.getSettingValue());
        request.setType(details.getType());
        request.setActive(details.getIsActive());
        request.setCreatedBy(details.getCreatedBy());
        request.setEditedBy(details.getEditedBy());
        request.setOrder(details.getDisplayOrder());

        return request;
    }

}
