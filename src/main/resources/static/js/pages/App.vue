<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">

    <v-app >
        <v-navigation-drawer class="v-navigation-drawer--absolute"
                             v-model="drawer"
                             :mini-variant.sync="mini"
                             hide-overlay
                             stateless
                             value="true"

        >
            <v-list>
                <v-list-tile>
                    <v-list-tile-action>
                        <v-icon>business</v-icon>
                    </v-list-tile-action>
                    <v-list-tile-title>Торговая площадка</v-list-tile-title>
                    <v-btn icon @click.stop="mini = !mini">
                        <v-icon>chevron_left</v-icon>
                    </v-btn>
                </v-list-tile>

                <v-container v-if="!profile">
                    <div>
                        <v-icon>account_circle</v-icon>
                        <v-btn
                           @click="goAuth"
                        >
                            Авторизоваться
                        </v-btn>
                    </div>
                </v-container>

                <v-list-group
                        v-if="profile"
                        prepend-icon="account_circle"
                        value="true"
                        >
                    <template v-slot:activator>
                        <v-list-tile>
                            <v-list-tile-title>{{ profile.username }}</v-list-tile-title>
                        </v-list-tile>
                    </template>
                    <v-list-group
                            no-action
                            sub-group
                            value="true"
                    >
                        <template v-slot:activator>
                            <v-list-tile>
                                <v-list-tile-title>Панель управления</v-list-tile-title>
                            </v-list-tile>
                        </template>

                        <v-list-tile @click="goUsers">
                            <v-list-tile-title v-text="'Пользователи'"></v-list-tile-title>
                            <v-list-tile-action>
                                <v-icon v-text="'people_outline'"></v-icon>
                            </v-list-tile-action>
                        </v-list-tile>

                        <v-list-tile @click="goBidding">
                            <v-list-tile-title v-text="'Настроить торги'"></v-list-tile-title>
                            <v-list-tile-action>
                                <v-icon v-text="'settings'"></v-icon>
                            </v-list-tile-action>
                        </v-list-tile>

                    </v-list-group>

                    <v-list-group
                            sub-group
                            no-action
                    >
                        <template v-slot:activator>
                            <v-list-tile>
                                <v-list-tile-title>Меню</v-list-tile-title>
                            </v-list-tile>
                        </template>

                        <v-list-tile @click="goProfile">
                            <v-list-tile-title v-text="'Профиль'"></v-list-tile-title>
                            <v-list-tile-action>
                                <v-icon v-text="'face'"></v-icon>
                            </v-list-tile-action>
                        </v-list-tile>

                        <v-list-tile @click="goBiddingChoose">
                            <v-list-tile-title v-text="'Торги'"></v-list-tile-title>
                            <v-list-tile-action>
                                <v-icon v-text="'trending_up'"></v-icon>
                            </v-list-tile-action>
                        </v-list-tile>

                        <v-list-tile @click="goPlay">
                            <v-list-tile-title v-text="'Выбранные позиции'"></v-list-tile-title>
                            <v-list-tile-action>
                                <v-icon v-text="'done'"></v-icon>
                            </v-list-tile-action>
                        </v-list-tile>

                        <v-list-tile @click="exit">
                            <v-list-tile-title v-text="'Выйти'"></v-list-tile-title>
                            <v-list-tile-action>
                                <v-icon v-text="'exit_to_app'"></v-icon>
                            </v-list-tile-action>
                        </v-list-tile>

                    </v-list-group>
                </v-list-group>
            </v-list>
        </v-navigation-drawer>

        <v-container>
            <router-view></router-view>
        </v-container>

    </v-app>

</template>

<script>
    import { mapState } from 'vuex'

    export default {
        data() {
            return {
                drawer: true,
                mini: false
            }
        },
        components : {

        },
        computed: mapState(['profile']),
        methods: {
            goProfile() {
                this.$router.push('/profile' + document.cookie)
            },
            goBiddingChoose() {
                this.$router.push('/bidding?' + document.cookie)
            },
            goPlay() {
                this.$router.push('/bidding/choose?' + document.cookie)
            },
            goAuth() {
                this.$router.push('/auth')
            },
            goUsers() {
                this.$router.push('/admin/?' + document.cookie)
            },
            goBidding() {
                this.$router.push('/admin/setting?' + document.cookie)
            },
            exit() {
                document.location.replace('/logout')
            }
        }
    }

</script>

<style>

</style>