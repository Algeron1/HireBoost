en HireBoost — Application Architecture
HireBoost is a web app for analyzing, translating and optimizing CVs using AI, targeting an international audience.

📊 Architecture Diagram
![Architecture Diagram](docs/ARCHITECTURE.png)

📃 Module Descriptions:
Authorization/Registration Service
Manages user registration and authorization:

Admin

HR

Applicant

Applicant Module
Allows applicants to:

Upload CV

Get analysis

Optimize CV

Translate CV

Generate CV letter

HR Module
Allows HR to:

View applicant responses

Download CVs

Resume Download Service
Handles uploading CVs and saving them to PostgreSQL and filesystem.

AI Service
Integrates with OpenAI API:

CV Analysis

CV Optimization

Translation

CV Letter Generation

Resume Analyzer
Analyzes CVs for job relevance.

Resume Optimizer
Optimizes CVs for selected job roles.

Translate Resume
Translates CVs into multiple languages.

CV Letter Generator
Generates cover letters based on CV and job role.

PostgreSQL Database
Stores users, resumes, analytics, metadata.

📌 Technologies:
Backend: Spring Boot

Frontend: React

Database: PostgreSQL

AI: OpenAI API



🇷🇺 HireBoost — Архитектура приложения
HireBoost — это веб-приложение для анализа, перевода и оптимизации резюме с помощью AI, ориентированное на международную аудиторию.

📊 Схема архитектуры
![Architecture Diagram](docs/ARCHITECTURE.png)

📃 Описание модулей:
Authorization/Registration Service
Управляет регистрацией и авторизацией пользователей:

Администратор

HR-менеджер

Соискатель

Applicant Module
Позволяет соискателю:

Загружать резюме

Получать анализ

Оптимизировать резюме

Переводить резюме

Генерировать сопроводительные письма

HR Module
Позволяет HR:

Просматривать отклики

Скачивать резюме

Resume Download Service
Отвечает за загрузку резюме, сохранение в PostgreSQL и файловую систему.

AI Service
Интеграция с OpenAI API:

Анализ резюме

Оптимизация резюме

Перевод

Генерация CV Letter

Resume Analyzer
Модуль анализа резюме на соответствие вакансии.

Resume Optimizer
Модуль улучшения резюме под вакансию.

Translate Resume
Модуль перевода резюме.

CV Letter Generator
Создаёт сопроводительное письмо по шаблону и резюме.

PostgreSQL Database
Хранение пользователей, резюме, отчетов, метаданных.

📌 Технологии:
Backend: Spring Boot

Frontend: React

Database: PostgreSQL

AI: OpenAI API

