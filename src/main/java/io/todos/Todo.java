package io.todos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Region("Todos")
class Todo implements Serializable {
    private Long id;
    private String title;
    private Boolean completed = Boolean.FALSE;
}
