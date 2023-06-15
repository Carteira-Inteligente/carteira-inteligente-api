package br.com.carteirainteligente.api.service;

import br.com.carteirainteligente.api.model.AutomaticCategory;
import br.com.carteirainteligente.api.model.Category;
import br.com.carteirainteligente.api.repository.AutomaticCategoryRepository;
import br.com.carteirainteligente.api.repository.CategoryRepository;
import com.lilittlecat.chatgpt.offical.ChatGPT;
import kong.unirest.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public AutomaticCategory getAutomaticCategory(String inputJson) {

        JSONObject jsonObject = new JSONObject(inputJson);
        String input = jsonObject.getString("input");
        input = input.toUpperCase();

        AutomaticCategory automaticCategory = automaticCategoryRepository.findTop1ByInput(input);

        if (automaticCategory != null) {
            return automaticCategory;
        } else {
            ChatGPT chatGPT = new ChatGPT("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");
            String response = null;
            //String response = chatGPT.ask("hello");
            //System.out.println(response); // will be "\n\nHello! How may I assist you today?"

            if (response == null || !response.equals("")) {

                Category category = categoryRepository.findTop1ByDescription(response);

                AutomaticCategory newAutomaticCategory = new AutomaticCategory();
                newAutomaticCategory.setInput(input);
                newAutomaticCategory.setResponse(response);
                newAutomaticCategory.setCategory(category);

                automaticCategoryRepository.save(newAutomaticCategory);

                return newAutomaticCategory;
            }

        }

        return null;
    }

}
