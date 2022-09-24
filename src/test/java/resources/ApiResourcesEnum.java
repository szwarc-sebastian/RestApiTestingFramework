package resources;

public enum ApiResourcesEnum {

    AddPlaceApi("/maps/api/place/add/json"),
    GetPlaceApi("/maps/api/place/get/json"),
    DeletePlaceApi("/maps/api/place/delete/json");

    private String resource;

    ApiResourcesEnum(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}
