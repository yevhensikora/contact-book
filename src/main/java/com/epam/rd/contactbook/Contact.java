package com.epam.rd.contactbook;

import java.util.Arrays;

public class Contact {
    String contactName;
    private int emailsCount;
    private int linksCount;
    ContactInfo[] contactInfos = new ContactInfo[10];


    public Contact(String contactName) {
        contactInfos[0] = new NameContactInfo(contactName);
    }

    private class NameContactInfo implements ContactInfo {

        private NameContactInfo (String name) {
            contactName = name;
        }

        @Override
        public String getTitle() {
            return "Name";
        }

        @Override
        public String getValue() {
            return contactName;
        }

    }
    public static class Email implements ContactInfo {
        private String email;

        public Email() {
        }

        public Email(String email) {
            this.email = email;
        }

        @Override
        public String getTitle() {
            return "Email";
        }

        @Override
        public String getValue() {
            return email;
        }
    }

    public void rename(String newName) {
        contactName = (newName != null && !newName.equals("")) ? newName : contactName;
        contactInfos[0] = new NameContactInfo(contactName);
    }

    public Email addEmail(String localPart, String domain) {
        if (emailsCount < 3) {
            if (localPart == null || domain == null) {
                return null;
            }
            Email email = new Email(localPart + "@" + domain);
            return add(email);
        }
        return null;
    }

    private Email add(Email email) {
        for (int i = 2; i < 5; i++) {
            if (contactInfos[i] == null) {
                contactInfos[i] = email;
                emailsCount++;
                return email;
            }
        }
        return null;
    }


    public Email addEpamEmail(String firstname, String lastname) {
        if (emailsCount < 3 || firstname == null || lastname == null) {
             Email epamEmail = new Email() {
                @Override
                public String getTitle() {
                    return "Epam Email";
                }

                @Override
                public String getValue() {
                    return firstname + "_" + lastname + "@" + "epam.com";
                }
            };
            return add(epamEmail);
        }
        return null;
    }



    public ContactInfo addPhoneNumber(int code, String number) {
        if (contactInfos[1] == null || code == 0 || number == null) {
            contactInfos[1] = new ContactInfo() {
                @Override
                public String getTitle() {
                    return "Tel";
                }

                @Override
                public String getValue() {
                    return "+" + code + " " + number;
                }
            };
            return contactInfos[1];
        }
        return null;
    }

    public static class Social implements ContactInfo {
        private final String id;
        private final String title;

        public Social(String id, String title) {
            this.id = id;
            this.title = title;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getValue() {
            return id;
        }

        @Override
        public String toString() {
            return title + ": " + id;
        }
    }

    public Social addTwitter(String twitterId) {
        return linksCount <= 10 || twitterId == null
                ? addLink(new Social(twitterId, "Twitter"))
                : null;
    }

    public Social addInstagram(String instagramId) {
        return linksCount <= 10 || instagramId == null
                ? addLink(new Social(instagramId, "Instagram"))
                : null;
    }

    private Social addLink(Social inst) {
        for (int i = 5; i < 10; i++) {
            if (contactInfos[i] == null) {
                contactInfos[i] = inst;
                linksCount++;
                return inst;
            }
        }
        return null;
    }

    public Social addSocialMedia(String title, String id) {
        return linksCount <= 10 || title == null || id == null
                ? addLink(new Social(id, title))
                : null;
    }

    public static ContactInfo[] removeTheElement (ContactInfo[] arr, int index) {
        ContactInfo[] anotherArray = new ContactInfo[arr.length - 1];
        for (int i = 0, k = 0; i < arr.length; i++) {
            if (i == index) {
                continue;
            }
            anotherArray[k++] = arr[i];
        }
        return anotherArray;
    }

    public ContactInfo[] getInfo() {
        for (int i = 0; i < contactInfos.length; i++){
            if (contactInfos[i] == null) {
                contactInfos = removeTheElement(contactInfos, i);
                i--;
            }
        }
        System.out.println(Arrays.toString(contactInfos));
        return contactInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (emailsCount != contact.emailsCount) return false;
        if (linksCount != contact.linksCount) return false;
        if (!contactName.equals(contact.contactName)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(contactInfos, contact.contactInfos);
    }

    @Override
    public int hashCode() {
        int result = contactName.hashCode();
        result = 31 * result + emailsCount;
        result = 31 * result + linksCount;
        result = 31 * result + Arrays.hashCode(contactInfos);
        return result;
    }

}


