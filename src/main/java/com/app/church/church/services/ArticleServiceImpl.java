package com.app.church.church.services;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.management.InvalidAttributeValueException;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.church.church.dto.ArticleDTO;
import com.app.church.church.entities.articles.Article;
import com.app.church.church.entities.articles.Comment;
import com.app.church.church.entities.articles.Content;
import com.app.church.church.entities.articles.Topic;
import com.app.church.church.entities.sections.Course;
import com.app.church.church.entities.users.Login;
import com.app.church.church.entities.users.Role;
import com.app.church.church.entities.users.User;
import com.app.church.church.repository.ArticleRepository;
import com.app.church.church.repository.CommentRepository;
import com.app.church.church.repository.ContentRepository;
import com.app.church.church.repository.LoginRepository;
import com.app.church.church.repository.RoleRepository;
import com.app.church.church.repository.TopicRepository;
import com.app.church.church.repository.UserRepository;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Article> findAllArticles() {
        return (List<Article>) this.articleRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Article findById(Long id) throws InvalidAttributeValueException {
        Optional<Article> article = this.articleRepository.findByIdWithAll(id);
        return article.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    @Override
    public Article addArticleWithType(ArticleDTO articleDTO, Long idUser) throws InvalidAttributeValueException {
        Optional<User> user = this.userRepository.findByIdWithArticles(idUser);
        Optional<Login> login = this.loginRepository.findById(idUser);
        if (user.isPresent()) {
            Article article = articleDTO.getArticle();
            Topic topic = articleDTO.getTopic();
            // From topic
            String type = topic.getType();
            boolean isCourse = topic.isCourse();
            //
            Set<Role> roles = login.get().getRoles();
            if (roles.size() == 1 && roles.contains(this.roleRepository.findByRole("ROLE_USER").get())
                    && !topic.isCourse()) {
                topic = this.topicRepository.findByType("COMMON")
                        .orElseGet(() -> this.topicRepository.save(new Topic("COMMON", false)));
            }else if(roles.contains(this.roleRepository.findByRole("ROLE_TEACHER").get()) && 
            isCourse && type.equals(user.get().getUserTutor().getName())){
                topic = this.topicRepository.findByType(topic.getType())
                        .orElseGet(() -> this.topicRepository.save(new Topic(user.get().getUserTutor().getName(), isCourse)));
            } else {
                topic = this.topicRepository.findByType(topic.getType())
                        .orElseGet(() -> this.topicRepository.save(new Topic(type, isCourse)));
            }

            article.getTopics().add(topic);
            article.setUserA(user.get());
            this.articleRepository.save(article);

        }

        return articleDTO.getArticle();
    }

    @Transactional
    @Override
    public Article updateArticleVideo(String video, Integer id, Long idUser) throws InvalidAttributeValueException {
        Optional<Article> article = this.articleRepository.findById(Long.valueOf(id));

        if (article.isPresent()) {
            if (this.isEnabled(Long.valueOf(id), idUser) || this.isTeacherFromCourse(Long.valueOf(id), idUser)) {
                article.get().setVideo(video);
                this.articleRepository.save(article.get());
                return article.get();
            }

        }
        return article.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Article> findAllArticlesWithVerification(boolean verification, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (verification) {

            List<Article> articles = new ArrayList<>();
            this.articleRepository.findAllWithVerification(verification, pageable)
                    .forEach(article -> {
                        article.getTopics().forEach(topic -> {
                            if (topic.getType().equals("COMMON")) {
                                articles.add(article);
                            }
                        });
                    });

            return new PageImpl<>(articles);
        }
        return this.articleRepository.findAllWithVerification(verification, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Article> findAllByTopics(String topic, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> articles = this.articleRepository.findAllByRolPage(topic, pageable);
        /*
         * List<Article> elements = new ArrayList<>();
         * articles.forEach(element -> {
         * element.getTopics().forEach(t -> {
         * if (t.getType().equals(topic)) {
         * elements.add(element);
         * }
         * });
         * });
         */
        return articles;
    }

    @Transactional
    @Override
    public Article updateArticleText(ArticleDTO articleDTO, Long id, Long idUser)
            throws InvalidAttributeValueException {
        Optional<Article> article = this.articleRepository.findById(id);

        if (article.isPresent()) {
            if (isEnabled(id, idUser) || isTeacherFromCourse(id, idUser)) {
                article.get().setTitle(articleDTO.getArticle().getTitle());
                article.get().setDescription(articleDTO.getArticle().getDescription());
                Topic topic = articleDTO.getTopic();
                String type = articleDTO.getTopic().getType();

                this.topicRepository.findByType(type)
                        .orElseGet(() -> this.topicRepository.save(topic));
                article.get().getTopics().add(topic);
                return this.articleRepository.save(article.get());
            }

        }
        return article.orElseThrow(InvalidAttributeValueException::new);

    }

    @Transactional
    @Override
    public Content saveContentMultimedia(MultipartFile media, Long id, String tipe, Long idUser)
            throws InvalidAttributeValueException, SerialException, SQLException, IOException {
        Optional<Content> content = this.contentRepository.findById(id);

        if (content.isPresent()) {
            Long idArticle = content.get().getArticleC().getId();
            if (this.isEnabled(idArticle, idUser) || isTeacherFromCourse(idArticle, idUser)) {
                Blob blobFile = new SerialBlob(media.getBytes());
                if (tipe.equals("IMAGE")) {
                    content.get().setImage(blobFile);
                } else {
                    content.get().setDocument(blobFile);
                }
                this.contentRepository.save(content.get());
                return content.get();
            }

        }
        return content.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    @Override
    public Content updateContentText(Content content, Long id, Long idUser) throws InvalidAttributeValueException {
        Optional<Content> contentR = this.contentRepository.findById(id);

        if (contentR.isPresent()) {
            Long idArticle = contentR.get().getArticleC().getId();
            if (isEnabled(idArticle, idUser) || isTeacherFromCourse(idArticle, idUser)) {
                contentR.get().setSubtitle(content.getSubtitle());
                contentR.get().setText(content.getText());
                this.contentRepository.save(contentR.get());
            }

        }
        return contentR.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    @Override
    public Article deleteArticle(Long id, Long idUser) throws InvalidAttributeValueException {
        Optional<Article> article = this.articleRepository.findById(id);
        if (article.isPresent()) {
            if(isEnabled(id, idUser) || isTeacherFromCourse(id, idUser)){
                this.articleRepository.delete(article.get());
            }
            
        }
        return article.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    public Article addContent(Content content, Long id, Long idUser) throws InvalidAttributeValueException {
        Optional<Article> article = this.articleRepository.findByIdWithAll(id);
        if (isEnabled(id, idUser) || isTeacherFromCourse(id, idUser)) {
            article.get().getContent().add(content);

            this.articleRepository.save(article.get());
        }
        return article.orElseThrow(InvalidAttributeValueException::new);

    }

    @Transactional
    @Override
    public Article deleteContent(Long idArticle, Long idContent, Long idUser) throws InvalidAttributeValueException {
        Optional<Article> article = this.articleRepository.findByIdWithAll(idArticle);
        if (isEnabled(idArticle, idUser) || isTeacherFromCourse(idArticle, idUser)) {
            article.get().getContent().forEach(content -> {
                if (content.getId() == idContent) {
                    article.get().getContent().remove(content);
                }
            });
            this.articleRepository.save(article.get());
        }

        return article.orElseThrow(InvalidAttributeValueException::new);

    }

    @Transactional
    @Override
    public Article addComment(Long idArticle, Long idUser, Comment comment) throws InvalidAttributeValueException {
        Optional<Article> article = this.articleRepository.findByIdWithAll(idArticle);
        Optional<User> user = this.userRepository.findById(idUser);
        if (article.isPresent() && user.isPresent()) {
            comment.setUser(user.get());
            comment.setArticle(article.get());
            this.commentRepository.save(comment);
        }
        return article.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    @Override
    public Article updateComment(Long id, Comment comment, Long idUser) throws InvalidAttributeValueException {
        Optional<Comment> com = this.commentRepository.findById(id);
        if (com.isPresent() && com.get().getUser().equals(this.userRepository.findById(idUser).get())) {
            com.get().setText(comment.getText());
            this.commentRepository.save(com.get());
        }
        Long idArticle = com.get().getArticle().getId();
        return this.articleRepository.findByIdWithAll(idArticle).orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    @Override
    public Article deleteComment(Long id, Long idUser) throws InvalidAttributeValueException {
        Optional<Comment> com = this.commentRepository.findById(id);
        Optional<Login> login = this.loginRepository.findById(idUser);
        Long idArticle = com.get().getUser().getId();
        if (com.isPresent()) {
           
            if (com.get().getUser().equals(userRepository.findById(idUser).get()) ||
                    login.get().getRoles().contains(this.roleRepository.findByRole("ROLE_ADMIN").get())
                    || login.get().getRoles().contains(this.roleRepository.findByRole("ROLE_PREACHER").get()) 
                    || isTeacherFromCourse(idArticle, idUser)) {
                        this.commentRepository.delete(com.get());
            }
            
        }
        
        return this.articleRepository.findByIdWithAll(idArticle).orElseThrow(InvalidAttributeValueException::new);
    }

    private boolean isEnabled(Long idArticle, Long idUser) {
        Optional<Login> login = this.loginRepository.findById(idUser);
        Optional<Article> article = this.articleRepository.findById(idArticle);

        if (login.isPresent() && article.isPresent()) {
            boolean aux = article.get().getUserA().equals(this.userRepository.findById(idUser).get());
            if (aux || login.get().getRoles().contains(this.roleRepository.findByRole("ROLE_ADMIN").get())
                    || login.get().getRoles().contains(this.roleRepository.findByRole("ROLE_PREACHER").get())) {
                return true;
            } else {
                throw new AccessDeniedException("You don't have permissions");
            }
        }
        return false;

    }

    private boolean isTeacherFromCourse(Long idArticle, Long idUser){
        Optional<Article> article = this.articleRepository.findByIdWithAll(idArticle);
        Optional<User> user = this.userRepository.findById(idUser);
        Optional<Login> login = this.loginRepository.findById(idUser);
        if(article.isPresent() && user.isPresent()){
            Set<Topic> topics = article.get().getTopics();
            boolean aux = false;
            Course course = user.get().getUserTutor();
            for(Topic topic: topics){
                if(topic.getType().equals(course.getName())){
                    aux = true;
                }
            }
            if(aux && login.get().getRoles().contains(roleRepository.findByRole("ROLE_TEACHER").get())){
                return true;
            }else{
                throw new AccessDeniedException("You don't have permissions.");
            }
        }
        return false;
    }

}
