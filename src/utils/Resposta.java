package utils;

public class Resposta {
    public String status;
    public String msg;

    public Resposta(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Resposta() {};

    @Override
    public String toString() {
        return "Resposta {\n\tstatus: "+status+",\n\tmensagem: "+msg+"\n}";
    }
}
