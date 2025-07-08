<b>en HireBoost — Application Architecture
HireBoost is a web app for analyzing, translating and optimizing CVs using AI, targeting an international audience.</b>

📊 Architecture Diagram

![Architecture Diagram](ARCHITECTURE.png)

📃 Module Descriptions:

<b>Authorization/Registration Service
Manages user registration and authorization:</b>

Admin

HR

Applicant

<b>Applicant Module
Allows applicants to:</b>

Upload CV

Get analysis

Optimize CV

Translate CV

Generate CV letter

<b>HR Module
HR personal dashboard:</b>

Create and upload job vacancies.

Upload applicants' CVs.

Get AI-powered CV analysis for job fit.

Automatic recommendations:

Key points to pay attention to.

Questions to ask during interviews.

<b>Resume Download Service
Handles uploading CVs and saving them to PostgreSQL and filesystem.</b>

<b>AI Service
Integrates with OpenAI API:</b>

CV Analysis

CV Optimization

Translation

CV Letter Generation

<b>Resume Analyzer</b>
Analyzes CVs for job relevance.

<b>Resume Optimizer</b>
Optimizes CVs for selected job roles.

<b>Translate Resume</b>
Translates CVs into multiple languages.

<b>CV Letter Generator</b>
Generates cover letters based on CV and job role.

<b>PostgreSQL Database</b>
Stores users, resumes, analytics, metadata.

<b>📌 Technologies:</b>
Backend: Spring Boot

Frontend: React

Database: PostgreSQL

AI: OpenAI API

______________________________________________________________________________________________________________________________________________________________________________________________________________________

<b>🇷🇺 HireBoost — Архитектура приложения
HireBoost — это веб-приложение для анализа, перевода и оптимизации резюме с помощью AI, ориентированное на международную аудиторию.</b>

📊 Схема архитектуры

![Architecture Diagram](ARCHITECTURE.png)

📃 Описание модулей:

<b>Authorization/Registration Service
Управляет регистрацией и авторизацией пользователей:</b>

Администратор

HR-менеджер

Соискатель

<b>Applicant Module
Позволяет соискателю:</b>

Загружать резюме

Получать анализ

Оптимизировать резюме

Переводить резюме

Генерировать сопроводительные письма

<b>HR Module
Личный кабинет HR-специалиста:</b>

Создание и загрузка вакансий.

Загрузка резюме соискателей.

Получение AI-анализа резюме на соответствие вакансии.

Автоматические рекомендации:

На какие пункты обратить внимание.

Какие вопросы задать кандидату на интервью.

<b>Resume Download Service</b>
Отвечает за загрузку резюме, сохранение в PostgreSQL и файловую систему.

<b>AI Service
Интеграция с OpenAI API:</b>

Анализ резюме

Оптимизация резюме

Перевод

Генерация CV Letter

<b>Resume Analyzer</b>
Модуль анализа резюме на соответствие вакансии.

<b>Resume Optimizer</b>
Модуль улучшения резюме под вакансию.

<b>Translate Resume</b>
Модуль перевода резюме.

<b>CV Letter Generator</b>
Создаёт сопроводительное письмо по шаблону и резюме.

<b>PostgreSQL Database</b>
Хранение пользователей, резюме, отчетов, метаданных.

<b>📌 Технологии:</b>
Backend: Spring Boot

Frontend: React

Database: PostgreSQL

AI: OpenAI API

