package br.com.carteirainteligente.api.service;

import br.com.carteirainteligente.api.enums.PeriodEnum;
import br.com.carteirainteligente.api.enums.TypeEnum;
import br.com.carteirainteligente.api.model.AutomaticCategory;
import br.com.carteirainteligente.api.model.Entry;
import br.com.carteirainteligente.api.model.PaymentType;
import br.com.carteirainteligente.api.model.User;
import br.com.carteirainteligente.api.repository.CategoryRepository;
import br.com.carteirainteligente.api.repository.EntryRepository;
import br.com.carteirainteligente.api.repository.PaymentTypeRepository;
import br.com.carteirainteligente.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryService {

    @Autowired
    EntryRepository entryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AutomaticCategoryService automaticCategoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PaymentTypeRepository paymentTypeRepository;

    public List<Entry> listEntries() {
        return entryRepository.findAll();
    }

    public Entry getEntry(Long id) {
        return entryRepository.findById(id).orElse(null);
    }

    public Entry saveEntry(Entry entry) {
        User user = userRepository.findById(entry.getUser().getId()).orElse(null);

        entry.setUser(user);
        return entryRepository.save(entry);
    }

    public Entry saveFastEntry(Entry entry) {
        User user = userRepository.findById(entry.getUser().getId()).orElse(null);
        entry.setUser(user);

        AutomaticCategory automaticCategory = automaticCategoryService.getAutomaticCategory(entry.getDescription());
        if (automaticCategory != null && automaticCategory.getCategory() != null) {
            entry.setCategory(automaticCategory.getCategory());
        } else {
            entry.setCategory(categoryRepository.findTop1ByDescription("Outros"));
        }

        entry.setPaid(Boolean.TRUE);
        entry.setPeriod(PeriodEnum.NOT_REPEAT);

        PaymentType paymentType = paymentTypeRepository.findTop1ByType(TypeEnum.WALLET);

        if (paymentType != null) {
            entry.setPaymentType(paymentType);
        }

        return entryRepository.save(entry);
    }

    public Entry updateEntry(Long id, Entry entry) {
        Entry existingEntry = entryRepository.findById(id).orElse(null);
        User user = userRepository.findById(entry.getUser().getId()).orElse(null);

        if(existingEntry != null) {
            existingEntry.setUser(user);
            existingEntry.setCategory(entry.getCategory());
            existingEntry.setPaymentType(entry.getPaymentType());
            existingEntry.setDescription(entry.getDescription());
            existingEntry.setPeriod(existingEntry.getPeriod());
            existingEntry.setPaidValue(entry.getPaidValue());
            existingEntry.setPaidDate(entry.getPaidDate());
            existingEntry.setPaid(entry.getPaid());
            existingEntry.setDueDate(entry.getDueDate());

            entryRepository.save(existingEntry);
            return existingEntry;
        }

        return null;
    }

    public Entry updateEntryPatch(Long id, Entry entry) {
        Entry existingEntry = entryRepository.findById(id).orElse(null);

        User user = null;
        if (entry.getUser() != null) {
            user = userRepository.findById(entry.getUser().getId()).orElse(null);
        }

        if(existingEntry != null) {
            if (user != null) {
                existingEntry.setUser(user);
            }
            existingEntry.setPaidDate(entry.getPaidDate());
            existingEntry.setPaid(entry.getPaid());

            entryRepository.save(existingEntry);
            return existingEntry;
        }

        return null;
    }

    public Entry deleteEntry(Long id) {
        Entry existingEntry = entryRepository.findById(id).orElse(null);

        if(existingEntry != null) {
            entryRepository.deleteById(id);
            return existingEntry;
        }

        return null;
    }
}
