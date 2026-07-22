package onthi.libapp.book;
import java.util.List;
import onthi.libapp.model.Book;

public interface BookSource {
    List<Book> loadBooks();
}   