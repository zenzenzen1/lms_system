package dto.response;


import dto.request.SettingRequest;
import entity.SettingDetails;
import java.util.List;

public class SettingResponse {
    private SettingRequest setting;
    private List<String> settingTypes;

    public SettingResponse() {
    }

    public SettingResponse(SettingRequest SessionResponse, List<String> settingTypes) {
        this.setting = setting;
        this.settingTypes = settingTypes;
    }

    public SettingRequest getSetting() {
        return setting;
    }

    public void setSetting(SettingRequest setting) {
        this.setting = setting;
    }

    public List<String> getSettingTypes() {
        return settingTypes;
    }

    public void setSettingTypes(List<String> settingTypes) {
        this.settingTypes = settingTypes;
    }
}