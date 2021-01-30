package be.alexandre01.inazuma.uhc.host.option;

public class HostButton {
    Type type;
    public HostButton(Type type){
        this.type = type;
    }
    public enum Type{
        CONTAINER,OPTION;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
