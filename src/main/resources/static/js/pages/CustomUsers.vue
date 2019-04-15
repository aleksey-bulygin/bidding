<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div class="user-table">
        <v-toolbar flat color="white">
            <v-toolbar-title>Пользователи</v-toolbar-title>
            <v-divider
                    class="mx-2"
                    inset
                    vertical
            ></v-divider>
            <v-spacer></v-spacer>
            <v-dialog v-model="dialog" max-width="500px">
                <template v-slot:activator="{ on }">
                    <v-btn color="primary" dark class="mb-2" v-on="on">Добавить пользователя</v-btn>
                </template>
                <v-card>
                    <v-card-title>
                        <span class="headline">Добавление данных пользователя</span>
                    </v-card-title>

                    <v-card-text>
                        <v-container grid-list-md>
                            <v-layout wrap>
                                <v-flex xs12 sm2 md4>
                                    <v-text-field v-model="editedItem.username" label="Логин"></v-text-field>
                                </v-flex>

                                <v-flex xs12 sm2 md4>
                                    <v-text-field v-model="editedItem.password" label="Пароль"></v-text-field>
                                </v-flex>

                                <v-flex xs12 sm6 md4>
                                    <v-text-field v-model="editedItem.firstName" label="Имя"></v-text-field>
                                </v-flex>

                                <v-flex xs12 sm6 md4>
                                    <v-text-field v-model="editedItem.lastName" label="Фамилия"></v-text-field>
                                </v-flex>

                                <v-flex xs12 sm6 md4>
                                    <v-text-field v-model="editedItem.email" label="e-mail"></v-text-field>
                                </v-flex>

                                <v-flex xs12 sm6 md4>
                                    <v-text-field v-model="editedItem.company" label="Компания"></v-text-field>
                                </v-flex>

                                <v-flex xs12 sm6 md4>
                                    <v-text-field v-model="editedItem.position" label="Должность"></v-text-field>
                                </v-flex>

                                <v-flex xs12 sm6 md4>
                                    <v-text-field v-model="editedItem.roles" label="Роль"></v-text-field>
                                </v-flex>
                            </v-layout>
                        </v-container>
                    </v-card-text>

                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="blue darken-1" flat @click="close">Отмена</v-btn>
                        <v-btn color="blue darken-1" flat @click="save">Сохранить</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-toolbar>
        <v-data-table
                :headers="headers"
                :items="users"
                small class="elevation-1"
        >
            <template v-slot:items="props">
                <td>{{ props.item.username}}</td>
                <td class="text-xs-center">{{ props.item.firstName}}</td>
                <td class="text-xs-center">{{ props.item.lastName }}</td>
                <td class="text-xs-center">{{ props.item.email }}</td>
                <td class="text-xs-center">{{ props.item.company }}</td>
                <td class="text-xs-center">{{ props.item.position }}</td>
                <td class="text-xs-center">{{ props.item.roles }}</td>
                <td class="justify-center layout px-0">
                    <v-icon
                            small
                            class="mr-2"
                            @click="editItem(props.item)"
                    >
                        edit
                    </v-icon>
                    <v-icon
                            small
                            @click="deleteItem(props.item)"
                    >
                        delete
                    </v-icon>
                </td>
            </template>
            
        </v-data-table>
    </div>
</template>

<script>
    import axios from 'axios'

    export default {
        data: () => ({
            users: [],
            dialog: false,
            headers: [
                {
                    text: 'Логин',
                    align: 'left',
                    sortable: false,
                    value: 'username'
                },
                { text: 'Имя', value: 'firstName' },
                { text: 'Фамилия', value: 'lastName' },
                { text: 'e-mail', value: 'email' },
                { text: 'Компания', value: 'company' },
                { text: 'Должность', value: 'position' },
                { text: 'Роль', value: 'roles' },
                { text: 'Действия', value: 'username', sortable: false }
            ],
            editedIndex: -1,
            editedItem: {
                id : 0,
                username: '',
                password: '',
                firstName: '',
                lastName: '',
                email: '',
                company: '',
                position: '',
                roles: ''
            },
            defaultItem: {
                username: '',
                password: '',
                firstName: '',
                lastName: '',
                email: '',
                company: '',
                position: '',
                roles: ''
            }
        }),
        watch: {
            dialog (val) {
                val || this.close()
            }
        },

        created () {
            axios({
                method: 'get',
                url: '/admin/setting/users?' + document.cookie,
                headers: { 'Content-type' : 'application/json' },
                data: {}
            }).then( response => {
                this.users = response.data
            });
        },

        methods: {
            editItem (item) {
                this.editedIndex = this.users.indexOf(item)
                this.editedItem = Object.assign({}, item)
                this.dialog = true
            },

            deleteItem (item) {
                const index = this.users.indexOf(item)
               // confirm('Are you sure you want to delete this item?') && this.users.splice(index, 1)
                axios({
                    method: 'delete',
                    url: 'admin/setting/users/' + item.id + '?' + document.cookie,
                    headers: { 'Content-type' : 'application/json' },
                    data: {}
                }).then( response => {
                   console.log(response.data)
                   this.users.splice(index, 1)
                });
            },

            close () {
                this.dialog = false
                setTimeout(() => {
                    this.editedItem = Object.assign({}, this.defaultItem)
                    this.editedIndex = -1
                }, 300)
            },

            save () {
                if (this.editedIndex > -1) {
                    axios({
                        method: 'post',
                        url: 'admin/setting/users?' + document.cookie,
                        headers: { 'Content-type' : 'application/json'},
                        data: {
                            id : this.editedItem.id,
                            username : this.editedItem.username,
                            password : this.editedItem.password,
                            firstName : this.editedItem.firstName,
                            lastName : this.editedItem.lastName,
                            email : this.editedItem.email,
                            company : this.editedItem.company,
                            position : this.editedItem.position,
                            roles : this.editedItem.roles
                        }
                    }).then( response => {
                        console.log(response.data)
                    });
                    Object.assign(this.users[this.editedIndex], this.editedItem)
                } else {
                    axios({
                        method: 'post',
                        url: 'admin/setting/users?' + document.cookie,
                        headers: { 'Content-type' : 'application/json'},
                        data: {
                            username : this.editedItem.username,
                            password : this.editedItem.password,
                            firstName : this.editedItem.firstName,
                            lastName : this.editedItem.lastName,
                            email : this.editedItem.email,
                            company : this.editedItem.company,
                            position : this.editedItem.position,
                            roles : this.editedItem.roles
                        }
                    }).then( response => {
                        console.log(response.data)
                    });
                    this.users.push(this.editedItem)
                }
                this.close()
            }
        }

    }
</script>

<style scoped>

</style>