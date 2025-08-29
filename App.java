package com.acapra.console;

import com.acapra.console.dao.UserDAO;
import com.acapra.console.dao.PetDAO;
import com.acapra.console.model.User;
import com.acapra.console.model.Pet;

import java.util.*;

public class App {
    private static final Scanner in = new Scanner(System.in);
    private static final UserDAO userDAO = new UserDAO();
    private static final PetDAO petDAO = new PetDAO();
    private static User logged;

    public static void main(String[] args) {
        System.out.println("ACAPRA Console (MySQL)");
        boolean loop = true;
        while (loop) {
            menu();
            String op = in.nextLine().trim();
            switch (op) {
                case "1": cadastrarUsuario(); break;
                case "2": login(); break;
                case "3": cadastrarPet(); break;
                case "4": listarUsuarios(); break;
                case "5": listarPets(); break;
                case "6": listarMeusPets(); break;
                case "0": loop = false; break;
                default: System.out.println("Opção inválida.");
            }
        }
        System.out.println("Encerrado.");
    }

    private static void menu() {
        System.out.println();
        System.out.println("1) Cadastrar um usuário");
        System.out.println("2) Logar");
        System.out.println("3) Cadastrar um animal");
        System.out.println("4) Listar os usuários");
        System.out.println("5) Listar os animais");
        System.out.println("6) Listar os meus animais");
        System.out.println("0) Sair");
        System.out.print("Escolha: ");
    }

    private static void cadastrarUsuario() {
        System.out.print("Nome: "); String nome = in.nextLine().trim();
        System.out.print("Email: "); String email = in.nextLine().trim();
        System.out.print("Senha: "); String senha = in.nextLine();
        boolean ok = userDAO.create(nome, email, senha);
        System.out.println(ok ? "Usuário cadastrado!" : "Falha ao cadastrar.");
    }

    private static void login() {
        System.out.print("Email: "); String email = in.nextLine().trim();
        System.out.print("Senha: "); String senha = in.nextLine();
        User u = userDAO.login(email, senha);
        if (u != null) { logged = u; System.out.println("Logado como " + u.getNome()); }
        else System.out.println("Email ou senha inválidos.");
    }

    private static void cadastrarPet() {
        if (logged == null) { System.out.println("Faça login primeiro."); return; }
        System.out.print("Nome: "); String nome = in.nextLine().trim();
        System.out.print("Espécie: "); String especie = in.nextLine().trim();
        System.out.print("Raça: "); String raca = in.nextLine().trim();
        System.out.print("Idade (Enter = nulo): "); String idadeStr = in.nextLine().trim();
        Integer idade = idadeStr.isEmpty() ? null : Integer.parseInt(idadeStr);
        System.out.print("Descrição: "); String desc = in.nextLine().trim();
        System.out.print("Foto URL: "); String foto = in.nextLine().trim();
        System.out.print("Cidade: "); String cidade = in.nextLine().trim();

        Pet p = new Pet();
        p.setNome(nome); p.setEspecie(especie); p.setRaca(raca);
        p.setIdade(idade); p.setDescricao(desc); p.setFotoUrl(foto);
        p.setCidade(cidade); p.setCriadoPor(logged.getId());
        boolean ok = petDAO.create(p);
        System.out.println(ok ? "Animal cadastrado! ID=" + p.getId() : "Falha ao cadastrar o animal.");
    }

    private static void listarUsuarios() {
        List<User> list = userDAO.listAll();
        if (list.isEmpty()) System.out.println("Nenhum usuário.");
        for (User u : list) System.out.println(u);
    }

    private static void listarPets() {
        List<Pet> list = petDAO.listAll();
        if (list.isEmpty()) System.out.println("Nenhum animal.");
        for (Pet p : list) System.out.println(p);
    }

    private static void listarMeusPets() {
        if (logged == null) { System.out.println("Faça login."); return; }
        List<Pet> list = petDAO.listByUser(logged.getId());
        if (list.isEmpty()) System.out.println("Você não cadastrou o animal.");
        for (Pet p : list) System.out.println(p);
    }
}
