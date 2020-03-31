package modele;

public class Space {
    private int id;
    private String name;
    private Indicator[] indocators;

    public Space(int id, String name, Indicator[] indicators){
        this.id = id;
        this.name = name;
        this.indocators = indicators;
    }
}
