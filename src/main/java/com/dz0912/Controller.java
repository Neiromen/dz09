package com.dz0912;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    private List<Theme> themes = new ArrayList<>();

    @PostMapping("/theme")
    public ResponseEntity<String> createTheme(@RequestBody String title) {
        themes.add(new Theme(title));
        return ResponseEntity.ok(title);
    }

    @DeleteMapping("/theme/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable int id) {
        themes.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/themes")
    public ResponseEntity<List<Theme>> getAllThemes() {
        return ResponseEntity.ok(themes);
    }

    @PutMapping("/theme/{id}")
    public ResponseEntity<String > updateTheme(@RequestBody String title, @PathVariable int id) {
        themes.set(id, new Theme(title));
        return ResponseEntity.ok(title);
    }

    @GetMapping("/themes/length")
    public ResponseEntity<Integer> getAllThemesLength() {
        return ResponseEntity.ok(themes.size());
    }

    @DeleteMapping("/themes/all")
    public ResponseEntity<Void> deleteAllThemes() {
        themes.clear();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/theme/{id}")
    public ResponseEntity<Theme> getTheme(@PathVariable int id) {
        return ResponseEntity.ok(themes.get(id));
    }

    @PostMapping("/theme/{id}/comment")
    public ResponseEntity<Void> createComment(@RequestBody Comment comment, @PathVariable int id) {
        Theme theme = themes.get(id);
        theme.comments.add(comment);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/theme/{themeId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int themeId, @PathVariable int commentId) {
        Theme theme = themes.get(themeId);
        theme.comments.remove(commentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/theme/{themeId}/comment/{commentId}")
    public ResponseEntity<Void> updateComment(@RequestBody Comment comment,
                                              @PathVariable int commentId,
                                              @PathVariable int themeId) {
        Theme theme = themes.get(themeId);
        theme.comments.set(commentId, comment);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/theme/{themeId}/comments")
    public ResponseEntity<List<Comment>> getAllComments(@PathVariable int themeId) {
        Theme theme = themes.get(themeId);
        return ResponseEntity.ok(theme.comments);
    }

    @GetMapping("/comments/{username}")
    public ResponseEntity<List<Comment>> getAllCommentsByUsername(@PathVariable String username) {
        List<Comment> comments = new ArrayList<>();
        for(Theme theme : themes) {
            for(Comment comment : theme.comments) {
                if(comment.getUsername().equals(username)) {
                    comments.add(comment);
                }
            }
        }
        return ResponseEntity.ok(comments);
    }

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
