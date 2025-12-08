package tn.hopital.model;

public enum Specialite {
    GENERALISTE,
    CARDIOLOGUE,
    DERMATOLOGUE,
    PEDIATRE,
    CHIRURGIEN,
    GYNECOLOGUE,
    NEUROLOGUE,
    RADIOLOGUE,
    OPHTALMOLOGUE;

    @Override
    public String toString() {
        // pour afficher correctement dans les ComboBox
        String name = name().toLowerCase().replace("_", " ");
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

	boolean isBlank() {
		// TODO Auto-generated method stub
		return false;
	}
}
