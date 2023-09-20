package com.example.spring_project.service;

import com.example.spring_project.dto.RequestBookDto;
import com.example.spring_project.dto.ResponseBookDto;
import com.example.spring_project.entity.Book;
import com.example.spring_project.exception.BadExceptionHandler;
import com.example.spring_project.exception.ResourceNotFoundExceptionHandler;
import com.example.spring_project.repository.BookRepository;
import com.example.spring_project.security.entity.User;
import com.example.spring_project.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    public Book save(Object id, RequestBookDto bookDto) {
        Optional<User> user = userRepository.findById(Long.valueOf((Integer) id));
        if (user.isPresent()) {
            Book book = new Book();
            book.setBookName(bookDto.getBookName());
            book.setBookPrice(bookDto.getBookPrice());
            book.setUser(user.get());
            bookRepository.save(book);
            return book;
        } else {
            throw new BadExceptionHandler("Book save error occurred");
        }
    }

    public Object findAllBook(Object userId) {
        Long writerId = Long.valueOf((Integer) userId);
        AtomicReference<Boolean> admin = new AtomicReference<>(false);
        Optional<User> user = userRepository.findById(writerId);
        if (user.isPresent()) {
            user.get().getRoles().forEach(u->{
                if(u.getRoleName().equals("admin")){
                   admin.set(true);
                }
            });
            List<Book> bookList = null;
            if(admin.get()){
                bookList = bookRepository.findAll();
                List<ResponseBookDto> responseBookDtoList = new ArrayList<>();
                for (Book book : bookList) {
                     ResponseBookDto responseBookDto = new ResponseBookDto();
                     responseBookDto.setBookName(book.getBookName());
                     responseBookDto.setBookPrice(book.getBookPrice());
                     responseBookDto.setUserName(book.getUser().getUserName());
                     responseBookDto.setEmail(book.getUser().getEmail());
                     responseBookDtoList.add(responseBookDto);
                }
                return responseBookDtoList;
            }else{
                bookList =  bookRepository.findAllByWriterId(writerId);
                List<ResponseBookDto> responseBookDtoList = new ArrayList<>();
                for (Book book : bookList) {
                    ResponseBookDto responseBookDto = new ResponseBookDto();
                    responseBookDto.setBookName(book.getBookName());
                    responseBookDto.setBookPrice(book.getBookPrice());
                    responseBookDto.setUserName(book.getUser().getUserName());
                    responseBookDto.setEmail(book.getUser().getEmail());
                    responseBookDtoList.add(responseBookDto);
                }
                return responseBookDtoList;
            }
        } else {
            throw new ResourceNotFoundExceptionHandler("User not found");
        }
    }
}
