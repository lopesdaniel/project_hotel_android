package danieldesenvolvedor.com.br.projecthotel.model;

import java.io.Serializable;

/**
 * Created by Alucard on 02/04/2016.
 */
public class Quarto implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    private String valor;
    private String tipo;
    private String disponivel;


    public Quarto() {
    }

    public Quarto(int id, String valor, String tipo, String disponivel) {
        this.id = id;
        this.valor = valor;
        this.tipo = tipo;
        this.disponivel = disponivel;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(String disponivel) {
        this.disponivel = disponivel;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Nº Quarto: " + getId();
    }


}
