package com.example.a30secondsgame.Models;

public class Models
{

    public class TaskFillInTheBlanks {
        private int id;
        public TaskFillInTheBlanks(int id) {
            this.id = id;
        }
    }

    public class Language {
        private int id;
        private String languageCode;
        public Language(int id, String languageCode) {
            this.id = id;
            this.languageCode = languageCode;
        }
    }

    public class QuestionTaskFillInTheBlanks {
        private int id;
        private int taskId;
        private int languageId;
        private String questionText;
        public QuestionTaskFillInTheBlanks(int id, int taskId, int languageId, String questionText) {
            this.id = id;
            this.taskId = taskId;
            this.languageId = languageId;
            this.questionText = questionText;
        }
    }

    public class AnswerTaskFillInTheBlanks {
        private int id;
        private int taskId;
        private int languageId;
        private String answerText;
        private boolean isCorrect;
        public AnswerTaskFillInTheBlanks(int id, int taskId, int languageId, String answerText, boolean isCorrect) {
            this.id = id;
            this.taskId = taskId;
            this.languageId = languageId;
            this.answerText = answerText;
            this.isCorrect = isCorrect;
        }
    }

    public class TaskMatchSynonyms {
        private int id;
        public TaskMatchSynonyms(int id) {
            this.id = id;
        }
    }

    public class AnswerTaskMatchSynonyms {
        private int id;
        private int taskId;
        private int languageId;
        private String answer1;
        private String answer2;
        public AnswerTaskMatchSynonyms(int id, int taskId, int languageId, String answer1, String answer2) {
            this.id = id;
            this.taskId = taskId;
            this.languageId = languageId;
            this.answer1 = answer1;
            this.answer2 = answer2;
        }
    }

    public class TaskMultipleChoice {
        private int id;
        public TaskMultipleChoice(int id) {
            this.id = id;
        }
    }

    public class QuestionTaskMultipleChoice {
        private int id;
        private int taskId;
        private int languageId;
        private String questionText;
        public QuestionTaskMultipleChoice(int id, int taskId, int languageId, String questionText) {
            this.id = id;
            this.taskId = taskId;
            this.languageId = languageId;
            this.questionText = questionText;
        }
    }

    public class AnswerTaskMultipleChoice {
        private int id;
        private int taskId;
        private int languageId;
        private String answerText;
        private boolean isCorrect;
        public AnswerTaskMultipleChoice(int id, int taskId, int languageId, String answerText, boolean isCorrect) {
            this.id = id;
            this.taskId = taskId;
            this.languageId = languageId;
            this.answerText = answerText;
            this.isCorrect = isCorrect;
        }
    }

    public class TaskTranslateSentences {
        private int id;
        public TaskTranslateSentences(int id) {
            this.id = id;
        }
    }

    public class AnswerTaskTranslateSentences {
        private int id;
        private int taskId;
        private int languageId;
        private String answer;
        public AnswerTaskTranslateSentences(int id, int taskId, int languageId, String answer) {
            this.id = id;
            this.taskId = taskId;
            this.languageId = languageId;
            this.answer = answer;
        }
    }
}
