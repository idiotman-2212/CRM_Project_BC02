package crm_project_02.filter;

public enum AuthList {
    ADMIN(1),
    LEADER(2),
    STAFF(3);
    private int value;
    AuthList(int value) {
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}