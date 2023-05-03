package com.pebblepost.todo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TodoDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String title;
    private boolean completed;

    public TodoDto() {}

    public TodoDto(Long id) {
        this.id = id;
    }
    public TodoDto(Long id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public static TodoDto fromEntity(Todo todo) {
        return TodoDto.builder().build();
    }

    public static Todo toEntity(TodoDto dto) {
        return Todo.builder().build();
    }

    private static Builder builder() {
        return new Builder();
    }

    private static class Builder {

        public TodoDto build() {
            return new TodoDto();
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
