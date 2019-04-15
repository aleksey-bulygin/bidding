<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-navigation-drawer class="v-navigation-drawer--absolute"
            stateless
            value="true"
    >
        <v-list>
            <v-list-tile>
                <v-list-tile-action>
                    <v-icon>business</v-icon>
                </v-list-tile-action>
                <v-list-tile-title>Торговая площадка</v-list-tile-title>
            </v-list-tile>

            <v-list-group
                    prepend-icon="account_circle"
                    value="true"
            >
                <template v-slot:activator>
                    <v-list-tile>
                        <v-list-tile-title>username</v-list-tile-title>
                    </v-list-tile>
                </template>
                <v-list-group
                        no-action
                        sub-group
                        value="true"
                >
                    <template v-slot:activator>
                        <v-list-tile>
                            <v-list-tile-title>Admin</v-list-tile-title>
                        </v-list-tile>
                    </template>

                    <v-list-tile
                            v-for="(admin, i) in admins"
                            :key="i"
                            @click=""
                    >
                        <v-list-tile-title v-text="admin[0]"></v-list-tile-title>
                        <v-list-tile-action>
                            <v-icon v-text="admin[1]"></v-icon>
                        </v-list-tile-action>
                    </v-list-tile>
                </v-list-group>

                <v-list-group
                        sub-group
                        no-action
                >
                    <template v-slot:activator>
                        <v-list-tile>
                            <v-list-tile-title>Actions</v-list-tile-title>
                        </v-list-tile>
                    </template>
                    <v-list-tile
                            v-for="(crud, i) in cruds"
                            :key="i"
                            @click=""
                    >
                        <v-list-tile-title v-text="crud[0]"></v-list-tile-title>
                        <v-list-tile-action>
                            <v-icon v-text="crud[1]"></v-icon>
                        </v-list-tile-action>
                    </v-list-tile>
                </v-list-group>
            </v-list-group>
        </v-list>
    </v-navigation-drawer>
</template>

<script>
    import {username} from "pages/Auth.vue";

    export default {
        data: () => ({
            admins: [
                ['Management', 'people_outline'],
                ['Settings', 'settings']
            ],
            cruds: [
                ['Create', 'add'],
                ['Read', 'insert_drive_file'],
                ['Update', 'update'],
                ['Delete', 'delete']
            ]
        })
    }
</script>

<style scoped>

</style>