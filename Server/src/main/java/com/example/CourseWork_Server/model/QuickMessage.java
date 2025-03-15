package com.example.CourseWork_Server.model;

import com.example.CourseWork_Server.enums.QuickMessageType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quick_messages")
@Entity
public class QuickMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String content;

  @Column(name = "quick_message_type")
  @Enumerated(EnumType.STRING)
  private QuickMessageType quickMessageType;

  @Column(name = "latitude")
  private double latitude;

  @Column(name = "longitude")
  private double longitude;

  @Column(name = "is_read")
  private boolean isRead;

  @ManyToOne
  @JoinColumn(name = "quick_message_box_id", referencedColumnName = "id", nullable = false)
  private QuickMessageBox quickMessageBox;
}
