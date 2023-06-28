package br.com.carteirainteligente.api.service;

import br.com.carteirainteligente.api.model.AutomaticCategory;
import br.com.carteirainteligente.api.model.Category;
import br.com.carteirainteligente.api.repository.AutomaticCategoryRepository;
import br.com.carteirainteligente.api.repository.CategoryRepository;
import com.lilittlecat.chatgpt.offical.ChatGPT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutomaticCategoryService {

    @Autowired
    AutomaticCategoryRepository automaticCategoryRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public AutomaticCategory saveAutomaticCategory(AutomaticCategory automaticCategory) {
        Category category = categoryRepository.findById(automaticCategory.getCategory().getId()).orElse(null);
        automaticCategory.setCategory(category);

        String input = automaticCategory.getInput().toUpperCase();
        automaticCategory.setInput(input);

        return automaticCategoryRepository.save(automaticCategory);
    }

    public AutomaticCategory getAutomaticCategory(String input) {
        input = input.toUpperCase();

        AutomaticCategory automaticCategory = automaticCategoryRepository.findTop1ByInput(input);

        if (automaticCategory != null) {
            return automaticCategory;
        } else {
            ChatGPT chatGPT = new ChatGPT("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");
            //String response = null;
            String response = chatGPT.ask(requestToAI(input));
            System.out.println(response); // will be "\n\nHello! How may I assist you today?"

            if (response != null && !response.equals("")) {

                Category category = categoryRepository.findTop1ByDescription(response);

                if (category != null) {
                    AutomaticCategory newAutomaticCategory = new AutomaticCategory();
                    newAutomaticCategory.setInput(input);
                    newAutomaticCategory.setResponse(response);
                    newAutomaticCategory.setCategory(category);

                    automaticCategoryRepository.save(newAutomaticCategory);

                    return newAutomaticCategory;
                }
            }
        }
        return null;
    }

    public String requestToAI(String input) {
        String request = "Dentre as categorias abaixo, me informe qual a mais adequada ao texto \"" + input + "\". Me responda somente a categoria mais adequada. Caso o texto não se encaixe em nenhuma das categorias listadas, favor definir em \"Outros\": ";

        List<Category> categories = categoryRepository.findByIsDefault(Boolean.TRUE);
        for (Category c : categories) {
            request = request.concat("\n"+c.getDescription());
        }

        return request;
    }

}
