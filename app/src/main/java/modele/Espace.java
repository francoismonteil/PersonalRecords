package modele;

import java.util.ArrayList;

public class Espace {
    private int idEspace;
    private String label;
    private String description;
    private int idEspace_has_Indicateur;
    private ArrayList<Indicateur> indicateurs;

    public Espace(int idEspace, String label, String description, int idEspace_has_Indicateur){
        this.idEspace = idEspace;
        this.label = label;
        this.description = description;
        this.idEspace_has_Indicateur = idEspace_has_Indicateur;
        this.indicateurs = new ArrayList<Indicateur>();
    }

    public int getIdEspace() {
        return idEspace;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Indicateur> getIndicateurs() {
        return indicateurs;
    }

    public void addIndicateur(Indicateur indicateur){
        this.indicateurs.add(indicateur);
    }

    public int getIdEspace_has_Indicateur() {
        return idEspace_has_Indicateur;
    }
}
