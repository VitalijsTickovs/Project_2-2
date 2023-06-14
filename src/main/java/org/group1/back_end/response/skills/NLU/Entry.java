package org.group1.back_end.response.skills.NLU;

import java.util.List;

public class Entry {
        private String text;
        private String intent;
        private List<Entity> entities;

        public Entry(String text, String intent, List<Entity> entities) {
            this.text = text;
            this.intent = intent;
            this.entities = entities;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getIntent() {
            return intent;
        }

        public void setIntent(String intent) {
            this.intent = intent;
        }

        public List<Entity> getEntities() {
            return entities;
        }

        public void setEntities(List<Entity> entities) {
            this.entities = entities;
        }
}
