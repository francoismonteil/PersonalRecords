package modele;

public class Indicateur {
    private int idIndicateur;
    private String type;
    private String label;

    public Indicateur(int idIndicateur, String type, String label){
        this.idIndicateur = idIndicateur;
        this.type = type;
        this.label = label;
    }

    public int getIdIndicateur() {
        return idIndicateur;
    }

    public void setIdIndicateur(int idIndicateur) {
        this.idIndicateur = idIndicateur;
    }

    public String getType() {
        return type;
    }

    public String getTypeDisplay() {
        String retour = "";
        switch(type){
            case "boolean":
                retour = "Oui ou Non";
                break;
            case "float":
                retour = "Nombre à virgule";
                break;
            case "integer":
                retour = "Nombre entier";
                break;
            case "picture":
                retour = "Image";
                break;
            case "checkbox":
                retour = "Case à cocher";
                break;
            default:
                retour = "Texte";
                break;
        }
        return retour;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Indicateur{" +
                "type='" + type + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
