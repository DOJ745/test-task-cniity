package com.example.springbootdemo.file_operations;

import com.example.springbootdemo.models.User;
import com.example.springbootdemo.structs.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class XmlAsyncReader
{
    private final ExecutorService executor;
    public static String DEFAULT_FILE_PATH = "src/main/resources/static/big-user-data.xml";
    public static int DEFAULT_THREAD_POOL_SIZE = 4;

    public XmlAsyncReader(int threadPoolSize)
    {
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
    }

    public interface UserHandler
    {
        void handleUser(User user);
    }

    public void readXmlFile(String filePath, UserHandler userHandler)
    {
        try
        {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler()
            {
                private final Stack<DefaultXmlTags> elementStack = new Stack<>();
                private final StringBuilder textBuffer = new StringBuilder();
                private User currentUser = null;
                private StructPersonalInfo currentPersonalInfo = null;
                private StructContactInfo currentContactInfo = null;
                private StructContactInfo.StructAddress currentAddress = null;
                private StructEmployment currentEmployment = null;
                private StructEducation currentEducation = null;
                private ArrayList<String> currentSkills = null;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
                {
                    DefaultXmlTags tag = DefaultXmlTags.fromString(qName);

                    if (tag == null)
                    {
                        return;
                    }

                    elementStack.push(tag);

                    switch (tag)
                    {
                        case TAG_USER:
                            currentUser = new User();

                            for (int i = 0; i < attributes.getLength(); i++)
                            {
                                DefaultXmlAttributes attr = DefaultXmlAttributes.fromString(attributes.getQName(i));

                                if (attr != null)
                                {
                                    switch (attr)
                                    {
                                        case ATTR_ID:
                                            currentUser.setId(Long.parseLong(attributes.getValue(i)));
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                            break;
                        case TAG_PERSONAL_INFO:
                            currentPersonalInfo = new StructPersonalInfo();
                            currentUser.setPersonalInfo(currentPersonalInfo);
                            break;
                        case TAG_CONTACT_INFO:
                            currentContactInfo = new StructContactInfo();
                            currentUser.setContactInfo(currentContactInfo);
                            break;
                        case TAG_ADDRESS:
                            currentAddress = new StructContactInfo.StructAddress();
                            currentContactInfo.setAddress(currentAddress);
                            break;
                        case TAG_EMPLOYMENT:
                            currentEmployment = new StructEmployment();
                            currentUser.setEmployment(currentEmployment);
                            break;
                        case TAG_EDUCATION:
                            currentEducation = new StructEducation();
                            currentUser.setEducation(currentEducation);
                            break;
                        case TAG_SKILLS:
                            currentSkills = new ArrayList<>();
                            currentUser.setSkills(currentSkills);
                            break;
                        default:
                            break;
                    }

                    textBuffer.setLength(0);
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException
                {
                    textBuffer.append(ch, start, length);
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException
                {
                    String text = textBuffer.toString().trim();

                    DefaultXmlTags tag = DefaultXmlTags.fromString(qName);

                    if (tag == null)
                    {
                        return; // Пропускаем неизвестные теги
                    }

                    switch (tag)
                    {
                        case TAG_FIRST_NAME:
                            currentPersonalInfo.setFirstName(text);
                            break;
                        case TAG_LAST_NAME:
                            currentPersonalInfo.setLastName(text);
                            break;
                        case TAG_EMAIL:
                            currentPersonalInfo.setEmail(text);
                            break;
                        case TAG_DATE_OF_BIRTH:
                            currentPersonalInfo.setDateOfBirth(text);
                            break;
                        case TAG_GENDER:
                            currentPersonalInfo.setGender(Genders.fromString(text));
                            break;
                        case TAG_PHONE_NUMBER:
                            currentContactInfo.setPhoneNumber(text);
                            break;
                        case TAG_STREET:
                            currentAddress.setStreet(text);
                            break;
                        case TAG_CITY:
                            currentAddress.setCity(text);
                            break;
                        case TAG_STATE:
                            currentAddress.setState(text);
                            break;
                        case TAG_POSTAL_CODE:
                            currentAddress.setPostalCode(Integer.parseInt(text));
                            break;
                        case TAG_COUNTRY:
                            currentAddress.setCountry(text);
                            break;
                        case TAG_COMPANY_NAME:
                            currentEmployment.setCompanyName(text);
                            break;
                        case TAG_POSITION:
                            currentEmployment.setPosition(text);
                            break;
                        case TAG_START_DATE:
                            currentEmployment.setStartDate(text);
                            break;
                        case TAG_END_DATE:
                            currentEmployment.setEndDate(text);
                            break;
                        case TAG_UNIVERSITY_NAME:
                            currentEducation.setUniversityName(text);
                            break;
                        case TAG_DEGREE:
                            currentEducation.setDegree(text);
                            break;
                        case TAG_GRADUATION_YEAR:
                            currentEducation.setGraduationYear(Integer.parseInt(text));
                            break;
                        case TAG_SKILL:
                            if (currentSkills != null)
                            {
                                currentSkills.add(text);
                            }
                            break;
                        case TAG_USER:
                            if (userHandler != null && currentUser != null)
                            {
                                System.out.println("Handling user with ID: " + currentUser.getId());

                                User userCopy = getUserCopy();

                                CompletableFuture.runAsync(() ->
                                {
                                    try
                                    {
                                        userHandler.handleUser(userCopy);
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }, executor);

                                currentUser = null;
                            }
                            break;
                        default:
                            break;
                    }

                    elementStack.pop();

                    textBuffer.setLength(0);
                }

                private User getUserCopy()
                {
                    User userCopy = new User();

                    userCopy.setId(currentUser.getId());

                    userCopy.setPersonalInfo(currentUser.getPersonalInfo() != null ?
                            new StructPersonalInfo(currentUser.getPersonalInfo()) : null);

                    userCopy.setContactInfo(currentUser.getContactInfo() != null ?
                            new StructContactInfo(currentUser.getContactInfo()) : null);

                    userCopy.setEmployment(currentUser.getEmployment() != null ?
                            new StructEmployment(currentUser.getEmployment()) : null);

                    userCopy.setEducation(currentUser.getEducation() != null ?
                            new StructEducation(currentUser.getEducation()) : null);

                    userCopy.setSkills(new ArrayList<>(currentUser.getSkills()));

                    return userCopy;
                }
            };

            saxParser.parse(new File(filePath), handler);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            executor.shutdown();

            try
            {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS))
                {
                    executor.shutdownNow();
                }
            }
            catch (InterruptedException e)
            {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}