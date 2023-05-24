package com.example.test.entity.email;

import lombok.Builder;
import lombok.Data;

/**
 * For sending emails to students
 */
@Data
@Builder
public class EmailDetails {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}