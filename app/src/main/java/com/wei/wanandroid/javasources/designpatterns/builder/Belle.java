package com.wei.wanandroid.javasources.designpatterns.builder;

/**
 * 美女
 */
public class Belle
{
    private int age;
    private int height;

    public Belle(Builder builder) {
        this.age = builder.age;
        this.height = builder.height;
    }

    public static class Builder
    {
        private int age;
        private int height;

        public Builder setAge(int age)
        {
            this.age = age;
            return this;
        }

        public Builder setHeight(int height)
        {
            this.height = height;
            return this;
        }

        public Belle create()
        {
            Belle belle = new Belle(this);
            return belle;
        }
    }

    public int getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }
}
