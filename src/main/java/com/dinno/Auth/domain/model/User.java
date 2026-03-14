package com.dinno.Auth.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {

    // Campos de usuario
    private UUID id;
    private String avatarUrl;
    private String username;
    private String email;
    private String passwordHash;

    // Cuenta Suspendida - eliminada
    private boolean isActive;
    private boolean isDeleted;

    // Campos de Auditoria
    private LocalDateTime deletedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(String username, String email, String passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isActive = true;
        this.isDeleted = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public User() {
    }

    public void suspendAccount() {
        this.isActive = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void deleteAccount() {
        this.isDeleted = true;
        this.isActive = false;
        this.deletedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void restoreAccount() {
        if (this.deletedAt != null && this.deletedAt.isBefore((LocalDateTime.now().minusDays(30)))) {
            throw new RuntimeException("El periodo de gracia de 30 días a expirado");
        }
        this.isDeleted = false;
        this.isActive = true;
        this.deletedAt = null;
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
