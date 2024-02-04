package br.edu.utfpr.cp.espjava.crudcidades.visao;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Controller
public class CidadeController {

    private Set<Cidade> cidades;

    CidadeController() {
        cidades = new HashSet<>();
    }

    @GetMapping("/")
    public String listar(Model memoria) {
        memoria.addAttribute("listaCidades", cidades);

        return "/crud";
    }
    @PostMapping("/criar")
    public String criar(Cidade cidade) {

        cidades.add(cidade);

        return "redirect:/";
    }

    @GetMapping("/excluir")
    public String excluir(
            @RequestParam String nome,
            @RequestParam String estado) {

        cidades.removeIf(c ->
                c.getNome().equals(nome) &&
                c.getEstado().equals(estado));

        return "redirect:/";
    }
}
