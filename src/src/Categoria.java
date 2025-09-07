public enum Categoria {
    SHOW,
    FESTA,
    ESPORTE,
    TEATRO,
    FEIRA,
    OUTROS;

    public static boolean valida(String valor) {
        for (Categoria c : values()) {
            if (c.name().equalsIgnoreCase(valor)) return true;
        }
        return false;
    }

    public static Categoria from(String valor) {
        for (Categoria c : values()) {
            if (c.name().equalsIgnoreCase(valor)) return c;
        }
        return OUTROS;
    }

    public static String opcoes() {
        StringBuilder sb = new StringBuilder();
        for (Categoria c : values()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(c.name());
        }
        return sb.toString();
    }
}
