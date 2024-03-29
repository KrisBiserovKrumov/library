package library.controllers;

import library.bindingModels.BookBindingModel;
import library.entities.Book;
import library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BookController {
    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {
        List<Book> bookList= this.bookRepository.findAll();
      modelAndView.setViewName("base-layout");
      modelAndView.addObject("view","book/index");
      modelAndView.addObject("books",bookList);

        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create(ModelAndView modelAndView) {
        modelAndView.setViewName("base-layout");
        modelAndView.addObject("view","book/Create");

        return modelAndView;
    }

    @PostMapping("/create")
    public String create(Book book) {
bookRepository.saveAndFlush(book);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Integer id, ModelAndView modelAndView) {
       Book bookToEdit = this.bookRepository.getOne(id);
       modelAndView.setViewName("base-layout");
       modelAndView.addObject("view", "book/Edit");
       modelAndView.addObject("book",bookToEdit);
        return modelAndView;
    }

    @PostMapping("edit/{id}")
    public String edit(@PathVariable(value = "id") Integer id, BookBindingModel bookBindingModel){
      Book bookFromDB = this.bookRepository.getOne(id);
      bookFromDB.setTitle(bookBindingModel.getTitle());
      bookFromDB.setAuthor(bookBindingModel.getAuthor());
      bookFromDB.setPrice(bookBindingModel.getPrice());
      this.bookRepository.saveAndFlush(bookFromDB);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView remove(@PathVariable(value = "id")Integer id, ModelAndView modelAndView){
        Book bookToRemove = this.bookRepository.findById(id).get();
        modelAndView.setViewName("base-layout");
        modelAndView.addObject("view","book/Remove");
        modelAndView.addObject("book",bookToRemove);
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public String remove(Book book){
      this.bookRepository.delete(book);
        return "redirect:/";
    }
}
