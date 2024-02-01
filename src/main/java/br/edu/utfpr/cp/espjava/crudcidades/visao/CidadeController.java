package br.edu.utfpr.cp.espjava.crudcidades.visao;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class CidadeController {

    @GetMapping("/")
    public String listar(Model memoria) {
        var cidades = Set.of(
                new Cidade("Curitiba", "Paraná"),
                new Cidade("Saquarema", "Rio de Janeiro"),
                new Cidade("Rio de Janeiro", "Rio de Janeiro")
        );

        memoria.addAttribute("listaCidades", cidades);

        return "/crud";
    }
}
