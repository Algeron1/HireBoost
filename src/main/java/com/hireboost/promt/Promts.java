package com.hireboost.promt;

public class Promts {

    public final static String RESUME_ANALYZE_PROMT_RU = "Проанализируй следующее резюме и оцени его по следующим критериям:\n" +
            "\n" +
            "1. **Структура и оформление** — насколько логично и понятно структурирована информация.\n" +
            "2. **Ясность и грамотность** — насколько понятно написан текст, наличие ошибок.\n" +
            "3. **Профессиональное содержание** — указаны ли ключевые навыки, достижения, образование и опыт, релевантные трудоустройству.\n" +
            "4. **Соответствие стандартам хорошего резюме** — насколько резюме выглядит профессионально, вызывает ли доверие у потенциального работодателя.\n" +
            "\n" +
            "Выведи:\n" +
            "- **Общую оценку от 0 до 100**, где 100 — идеальное резюме.\n" +
            "- Не делай предположений о профессии, оценивай универсально.  будь максимально строг и честен без заискиваний и сглаживаний.\n" +
            "- Ответ дай строго в формате JSON:\n" +
            "- В ответе дай только число и ничего больше:\n" +
            "\n" +
            "```json\n" +
            "{\n" +
            "  \"score\": 0\n" +
            "}\n" +
            "текст резюме: %s";

    public final static String RESUME_ANALYZE_PROMT_EN = "Analyze the following resume and rate it on the following criteria:\n" +
            "\n" +
            "1. **Structure and design** — how logically and clearly the information is structured.\n" +
            "2. **Clarity and literacy** — how clearly the text is written, the presence of errors.\n" +
            "3. **Professional content** — whether the key skills, achievements, education and experience relevant to employment are indicated.\n" +
            "4. **Compliance with the standards of a good resume** — how professional does the resume look, does it inspire confidence in a potential employer.\n" +
            "\n" +
            "Display:\n" +
            "- **Overall rating from 0 to 100**, where 100 is the perfect resume.\n" +
            "- Do not make assumptions about the profession, evaluate universally. Be as strict and honest as possible without fawning or smoothing over.\n" +
            "- Give a strict answer in JSON format:\n" +
            "- Give only the number and nothing else in the answer:\n" +
            "\n" +
            "```json\n" +
            "{\n" +
            " \"score\": 0\n" +
            "}\n" +
            "resume text: %s";

    public static final String RESUME_FULL_ANALYSIS_PROMT_RU = """
            Ты профессиональный карьерный консультант.
            
            Ниже представлено резюме кандидата и описание вакансии. 
            Твоя задача — провести комплексный анализ по следующим критериям:
            1. **Релевантность** резюме требованиям вакансии (указать «да» или «нет» и пояснить почему)
            2. **Стиль и структура** (насколько читаемо, логично и профессионально оформлено)
            3. Дай **оценку от 1 до 10**
            4. Приведи **конкретные рекомендации**, что улучшить, чтобы кандидат выглядел более привлекательным
            
            Формат вывода:
            Релевантность: [да/нет]. Пояснение: ...
            Стиль и структура: ...
            Оценка: ...
            Рекомендации:
            
            Резюме:
            %s""";

    public static final String RESUME_FULL_ANALYSIS_PROMT_EN = """
            You are a professional career consultant.
            
            Below is a candidate's resume and a job description.
            
            Your task is to conduct a comprehensive analysis based on the following criteria:
            1. **Relevance** of the resume to the job requirements (indicate "yes" or "no" and explain why)
            2. **Style and structure** (how readable, logical and professional it is)
            3. Give a **score from 1 to 10**
            4. Provide **specific recommendations** on what to improve to make the candidate look more attractive
            
            Output format:
            Relevance: [yes/no]. Explanation: ...
            Style and structure: ...
            Score: ...
            Recommendations:
            
            Resume:
            %s""";

    public static final String COV_LETTER_GENERATION_RU = "Ты профессиональный HR-эксперт и карьерный консультант. Составь уникальное, убедительное и грамотно структурированное сопроводительное письмо для кандидата, опираясь на его резюме, название компании и желаемую позицию.\n" +
            "\n" +
            "Требования:\n" +
            "- Письмо должно быть адресовано компании «%s» по позиции «%s».\n" +
            "- Учитывай опыт, навыки и достижения, представленные в резюме.\n" +
            "- Используй уверенный, но вежливый тон.\n" +
            "- Не переписывай дословно резюме, а адаптируй под конкретную вакансию.\n" +
            "- В конце письма добавь благодарность и готовность обсудить кандидатуру лично.\n" +
            "\n" +
            "Резюме кандидата:\n" +
            "%s\n";

    public static final String COV_LETTER_GENERATION_EN = "You are a professional HR expert and career advisor. Write a unique, persuasive, and well-structured cover letter for a candidate based on their resume, the company name, and the desired position.\n" +
            "\n" +
            "Requirements:\n" +
            "- The letter must be addressed to the company \"%s\" for the position \"%s\".\n" +
            "- Take into account the candidate's experience, skills, and accomplishments from the resume.\n" +
            "- Use a confident but polite tone.\n" +
            "- Do not copy the resume verbatim — adapt it to the specific role and company.\n" +
            "- End the letter with gratitude and willingness to discuss the application in person.\n" +
            "\n" +
            "Candidate's resume:\n" +
            "%s\n";

}
