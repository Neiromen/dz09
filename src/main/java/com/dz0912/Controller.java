package com.dz0912;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class Controller {

    private List<Theme> themes = new ArrayList<>();

    // curl -X POST http://localhost:8080/theme -H "Content-Type: application/json" -d "\"Новая тема\""
    @PostMapping("/theme")
    public ResponseEntity<String> createTheme(@RequestBody String title) {
        themes.add(new Theme(title));
        return ResponseEntity.ok(title);
    }

    // curl -X DELETE http://localhost:8080/theme/0
    @DeleteMapping("/theme/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable int id) {
        if (id < 0 || id >= themes.size())
        {
            return ResponseEntity.badRequest().body(null);
        }
        themes.remove(id);
        return ResponseEntity.noContent().build();
    }

    // curl http://localhost:8080/themes
    // curl http://localhost:8080/themes?sortOrder=minToMax
    // curl http://localhost:8080/themes?sortOrder=maxToMin
    @GetMapping("/themes")
    public ResponseEntity<List<Theme>> getAllThemes(@RequestParam(required = false) String sortOrder) {
        if (sortOrder == null || sortOrder.equals("minToMax")) {
            return ResponseEntity.ok(themes);
        } else if (Objects.equals(sortOrder, "maxToMin")) {
            return ResponseEntity.ok(themes.reversed());
        }
        return ResponseEntity.noContent().build();
    }

    // curl -X PUT http://localhost:8080/theme/0 -H "Content-Type: application/json" -d "\"Обновленная тема\""
    @PutMapping("/theme/{id}")
    public ResponseEntity<String > updateTheme(@RequestBody String title, @PathVariable int id) {
        if (id < 0 || id >= themes.size())
        {
            return ResponseEntity.badRequest().body(null);
        }
        themes.set(id, new Theme(title));
        return ResponseEntity.ok(title);
    }

    // curl http://localhost:8080/themes/length
    @GetMapping("/themes/length")
    public ResponseEntity<Integer> getAllThemesLength() {
        return ResponseEntity.ok(themes.size());
    }

    // curl -X DELETE http://localhost:8080/themes/all
    @DeleteMapping("/themes/all")
    public ResponseEntity<Void> deleteAllThemes() {
        themes.clear();
        return ResponseEntity.noContent().build();
    }

    // curl http://localhost:8080/theme/0
    @GetMapping("/theme/{id}")
    public ResponseEntity<Theme> getTheme(@PathVariable int id) {
        return ResponseEntity.ok(themes.get(id));
    }

    /*
    curl -X POST http://localhost:8080/theme/0/comment \
    -H "Content-Type: application/json" \
    -d '{"username": "user1", "text": "Мой комментарий"}'
    */
    @PostMapping("/theme/{id}/comment")
    public ResponseEntity<Void> createComment(@RequestBody Comment comment, @PathVariable int id) {
        if (id < 0 || id >= themes.size())
        {
            return ResponseEntity.badRequest().body(null);
        }
        Theme theme = themes.get(id);
        comment.setDate(new Date());
        theme.comments.add(comment);
        return ResponseEntity.ok().build();
    }

    // curl -X DELETE http://localhost:8080/theme/0/comment/0
    @DeleteMapping("/theme/{themeId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int themeId, @PathVariable int commentId) {
        if (themeId < 0 || themeId >= themes.size())
        {
            return ResponseEntity.badRequest().body(null);
        }
        Theme theme = themes.get(themeId);

        if (commentId < 0 || commentId >= theme.comments.size())
        {
            return ResponseEntity.badRequest().body(null);
        }
        theme.comments.remove(commentId);
        return ResponseEntity.ok().build();
    }

    /*
    curl -X PUT http://localhost:8080/theme/0/comment/0 \
    -H "Content-Type: application/json" \
    -d '{"username": "user1", "text": "Обновленный комментарий"}'
    */
    @PutMapping("/theme/{themeId}/comment/{commentId}")
    public ResponseEntity<Void> updateComment(@RequestBody Comment comment,
                                              @PathVariable int commentId,
                                              @PathVariable int themeId) {
        if (themeId < 0 || themeId >= themes.size())
        {
            return ResponseEntity.badRequest().body(null);
        }
        Theme theme = themes.get(themeId);

        if (commentId < 0 || commentId >= theme.comments.size())
        {
            return ResponseEntity.badRequest().body(null);
        }
        comment.setDate(new Date());
        theme.comments.set(commentId, comment);
        return ResponseEntity.ok().build();
    }

    // curl http://localhost:8080/theme/0/comments
    // curl http://localhost:8080/theme/0/comments?sortOrder=minToMax
    // curl http://localhost:8080/theme/0/comments?sortOrder=maxToMin
    @GetMapping("/theme/{themeId}/comments")
    public ResponseEntity<List<Comment>> getAllComments(@PathVariable int themeId,
                                                        @RequestParam(required = false) String sortOrder) {
        if (themeId < 0 || themeId >= themes.size())
        {
            return ResponseEntity.badRequest().body(null);
        }
        Theme theme = themes.get(themeId);
        if  (sortOrder == null || sortOrder.equals("minToMax")) {
            return ResponseEntity.ok(theme.comments);
        } else if (Objects.equals(sortOrder, "maxToMin")) {
            return ResponseEntity.ok(theme.comments.reversed());
        }
        return ResponseEntity.noContent().build();
    }

    // curl http://localhost:8080/comments/user1
    // curl http://localhost:8080/comments/user1?sortOrder=minToMax
    // curl http://localhost:8080/comments/user1?sortOrder=maxToMin
    @GetMapping("/comments/{username}")
    public ResponseEntity<List<Comment>> getAllCommentsByUsername(@PathVariable String username,
                                                                  @RequestParam(required = false) String sortOrder) {
        List<Comment> comments = new ArrayList<>();
        for(Theme theme : themes) {
            for(Comment comment : theme.comments) {
                if(comment.getUsername().equals(username)) {
                    comments.add(comment);
                }
            }
        }
        if  (sortOrder == null || sortOrder.equals("minToMax")) {
            return ResponseEntity.ok(comments);
        } else if (Objects.equals(sortOrder, "maxToMin")) {
            return ResponseEntity.ok(comments.reversed());
        }
        return ResponseEntity.noContent().build();    }

    // curl -X DELETE http://localhost:8080/comments/user1
    @DeleteMapping("/comments/{username}")
    public ResponseEntity<Void> deleteAllCommentsByUsername(@PathVariable String username) {
        for(Theme theme : themes) {
            for(Comment comment : theme.comments) {
                if(comment.getUsername().equals(username)) {
                    theme.comments.remove(comment);
                }
            }
        }
        return ResponseEntity.noContent().build();
    }
}