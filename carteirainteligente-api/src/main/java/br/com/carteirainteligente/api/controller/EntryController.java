package br.com.carteirainteligente.api.controller;

import br.com.carteirainteligente.api.model.Entry;
import br.com.carteirainteligente.api.service.EntryService;
import br.com.carteirainteligente.api.validator.EntryValidator;
import kong.unirest.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entry")
public class EntryController {

    @Autowired
    private EntryService entryService;

    @Autowired
    private EntryValidator entryValidator;

    @GetMapping
    public ResponseEntity<List<Entry>> listEntries() {
        List<Entry> entries = entryService.listEntries();
        return entries.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(entries) : ResponseEntity.ok(entries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entry> getEntry(@PathVariable Long id) {
        Entry entry = entryService.getEntry(id);
        return entry == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(entry);
    }

    @PostMapping
    public ResponseEntity<?> saveEntry(@RequestBody Entry entry, BindingResult result) {
        entryValidator.validate(entry, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Entry savedEntry = entryService.saveEntry(entry);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntry);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable Long id, @RequestBody Entry entry, BindingResult result) {
        entryValidator.validate(entry, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Entry updatedEntry = entryService.updateEntry(id, entry);
        return updatedEntry == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updatedEntry);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateEntryPatch(@PathVariable Long id, @RequestBody Entry entry, BindingResult result) {
        entryValidator.validatePatch(entry, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Entry updatedEntry = entryService.updateEntryPatch(id, entry);
        return updatedEntry == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updatedEntry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Entry> deleteEntry(@PathVariable Long id) {
        Entry deletedEntry = entryService.deleteEntry(id);
        return deletedEntry == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(deletedEntry);
    }
}
