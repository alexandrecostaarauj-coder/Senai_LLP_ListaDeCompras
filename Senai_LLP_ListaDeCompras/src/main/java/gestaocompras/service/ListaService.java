package gestaocompras.service;

import gestaocompras.model.Lista;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListaService {

    private static final String PASTA = "listas";
    private List<Lista> listas;

    public ListaService() {
        new File(PASTA).mkdirs();
        this.listas = carregarTodas();
    }

    public void salvar(Lista lista) {
        // atualiza ou adiciona na lista em memória
        boolean existe = false;
        for (int i = 0; i < listas.size(); i++) {
            if (listas.get(i).getNome().equals(lista.getNome())) {
                listas.set(i, lista);
                existe = true;
                break;
            }
        }
        if (!existe) listas.add(lista);

        // persiste em arquivo
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(PASTA + "/" + lista.getNome() + ".dat"))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Erro ao salvar lista: " + e.getMessage());
        }
    }

    public List<Lista> getListas() {
        return listas;
    }

    public Lista buscarPorNome(String nome) {
        for (Lista l : listas) {
            if (l.getNome().equals(nome)) return l;
        }
        return null;
    }

    private List<Lista> carregarTodas() {
        List<Lista> resultado = new ArrayList<>();
        File pasta = new File(PASTA);
        File[] arquivos = pasta.listFiles((d, name) -> name.endsWith(".dat"));
        if (arquivos == null) return resultado;

        for (File arquivo : arquivos) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                Lista lista = (Lista) ois.readObject();
                resultado.add(lista);
            } catch (Exception e) {
                System.out.println("Erro ao carregar: " + arquivo.getName());
            }
        }
        return resultado;
    }
}
