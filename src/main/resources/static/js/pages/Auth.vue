<template>
    <form class="auth">
        <v-text-field
                v-model="username"
                :error-messages="nameErrors"
                :counter="10"
                label="Логин"
                required
                @input="$v.username.$touch()"
                @blur="$v.username.$touch()"
        ></v-text-field>

        <v-text-field
                    v-model="password"
                    :append-icon="show? 'visibility' : 'visibility_off'"
                    :rules="[rules.required, rules.min]"
                    :type="show? 'text' : 'password'"
                    name="input-10-1"
                    label="Пароль"
                    hint="Не менее 2 символов"
                    counter
                    @click:append="show = !show"
        ></v-text-field>

        <v-btn @click="submit">Войти</v-btn>
        <v-btn @click="clear">Отмена</v-btn>
    </form>
</template>

<script>
    import { validationMixin } from 'vuelidate'
    import { required, maxLength, email } from 'vuelidate/lib/validators'
    import axios from 'axios'

    export default {
        name: 'Auth',
        mixins: [validationMixin],

        validations: {
            username: { required, maxLength: maxLength(10) },
            password: { required },
        },

        data: () => ({
            username: '',
            password: '',
            show: false,
            rules: {
                required: value => !!value || 'Неверные данные',
                min: v => v.length >= 2 || 'Не менее 2 символов',
            },
            token: ''
        }),

        computed: {
            nameErrors () {
                const errors = []
                if (!this.$v.username.$dirty) return errors
                !this.$v.username.maxLength && errors.push('Менее 10 символов')
                !this.$v.username.required && errors.push('Неверные данные')
                return errors
            }
        },

        methods: {
            submit () {
                this.$v.$touch()

                var authForm = new URLSearchParams();
                authForm.append('grant_type', 'password');
                authForm.append('username', this.username);
                authForm.append('password', this.password);

                axios({
                    method: 'post',
                    url: 'oauth/token',
                    auth: {username: 'client', password: 'secret'},
                    headers: {accept : 'application/x-www-form-urlencoded'},
                    data : authForm
                }).then(function (response) {
                   console.log(response.data);
                   document.cookie = "access_token=" + response.data.access_token;
                   document.location.replace('/?' + document.cookie )
                });

            },
            clear () {
                this.$v.$reset()
                this.username = ''
                this.password = ''
            }
        }
    }
</script>

<style scoped>
    .auth{
        margin: 25%;
    }
</style>